/*
 * MIT License
 *
 * Copyright (c) 2018 leobert-lan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package osp.leobert.android.pandorasample.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.Arrays;
import java.util.List;

import osp.leobert.android.pandora.Pandora;
import osp.leobert.android.pandora.RealDataSet;
import osp.leobert.android.pandora.rv.DataSet;
import osp.leobert.android.pandora.rv.PandoraRealRvDataSet;
import osp.leobert.android.pandorasample.cases.CompositeOpsInWrapTestActivity;
import osp.leobert.android.pandorasample.cases.DataChangeTestActivity;
import osp.leobert.android.pandorasample.cases.DataPropertyChangeTestActivity;
import osp.leobert.android.pandorasample.cases.MultiTypeTestActivity;
import osp.leobert.android.pandorasample.R;
import osp.leobert.android.pandorasample.RvAdapter;
import osp.leobert.android.pandorasample.dvh.AbsViewHolder;
import osp.leobert.android.pandorasample.kt.TestKtActivity;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    PandoraRealRvDataSet<DataSet.Data> dataSet;
    RvAdapter<PandoraRealRvDataSet<DataSet.Data>> adapter;

    private static final class Foo implements MenuVO2 {
        String name;
        Class<? extends Activity> activityClz;

        String subTitle;

        public Foo setSubTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Foo(String name, Class<? extends Activity> activityClz) {
            this.name = name;
            this.activityClz = activityClz;
        }

        @Override
        public int level() {
            return Level.l1;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getSubTitle() {
            return subTitle;
        }

        @Override
        public void setToViewHolder(AbsViewHolder<DataSet.Data> viewHolder) {
            viewHolder.setData(this);
        }
    }

    List<Foo> cases = Arrays.asList(
            new Foo("数据分组测试", DataChangeTestActivity.class)
                    .setSubTitle("列表进行虚拟分组，针对组进行定向更新"),
            new Foo("数据属性变化测试", DataPropertyChangeTestActivity.class)
                    .setSubTitle("依赖于equals和hash"),
            new Foo("Wrap数据集中的组合操作测试", CompositeOpsInWrapTestActivity.class)
                    .setSubTitle("在Wrapper类型数据集中，遍历删除所有的Type2Impl数据"),
            new Foo("多样式&一套数据，多处使用", MultiTypeTestActivity.class)
                    .setSubTitle("慎用，务必厘清原理再使用!!"),
            new Foo("pandora_rv_kt demo", TestKtActivity.class)
                    .setSubTitle("pandora_rv_kt 简单演示，参考pandora_rv的使用即可")
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);
        initDataSet();

        adapter = new RvAdapter<>(dataSet);
        Pandora.bind2RecyclerViewAdapter(dataSet.getRealDataSet(), adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.color.colorAccent));
        recyclerView.addItemDecoration(decoration);

        findViewById(R.id.b1).setVisibility(View.GONE);

        findViewById(R.id.b2).setVisibility(View.GONE);

        findViewById(R.id.b3).setVisibility(View.GONE);

    }

    private void initDataSet() {
        RealDataSet<DataSet.Data> wrapperDataSet = Pandora.real();
        dataSet = new PandoraRealRvDataSet<>(wrapperDataSet);


        dataSet.registerDVRelation(Foo.class, new MenuVH2.Creator(new MenuVH2.ItemInteract() {
            @Override
            public void onMenuItemClicked(int pos, MenuVO2 data) {
                if (pos >= 0 && pos < cases.size()) {
                    Intent intent = new Intent(MenuActivity.this, cases.get(pos).activityClz);
                    startActivity(intent);
                }
            }
        }));
        Pandora.addAll(dataSet.getRealDataSet(), cases);
    }
}
