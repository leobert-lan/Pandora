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

package osp.leobert.android.pandorasample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import osp.leobert.android.pandora.Pandora;
import osp.leobert.android.pandora.RealDataSet;
import osp.leobert.android.pandora.rv.DataSet;
import osp.leobert.android.pandora.rv.PandoraRealRvDataSet;
import osp.leobert.android.pandorasample.dvh.Type1VH;
import osp.leobert.android.pandorasample.dvh.Type1VO;
import osp.leobert.android.pandorasample.dvh.Type1VOImpl;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    PandoraRealRvDataSet<DataSet.Data> dataSet;
    RvAdapter<PandoraRealRvDataSet<DataSet.Data>> adapter;

    private static final class Foo {
        String name;
        Class<? extends Activity> activityClz;

        public Foo(String name, Class<? extends Activity> activityClz) {
            this.name = name;
            this.activityClz = activityClz;
        }
    }

    List<Foo> cases = Arrays.asList(
            new Foo("\r多样式\r", MainActivity.class),
            new Foo("\r数据测试\r", DataChangeTestActivity.class),
            new Foo("\r数据测试2-属性变化\r", DataPropertyChangeTestActivity.class),
            new Foo("\rWrap数据集中的组合操作测试\r", CompositeOpsInWrapTestActivity.class)

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


        dataSet.registerDVRelation(Type1VOImpl.class, new Type1VH.Creator(new Type1VH.ItemInteract() {
            @Override
            public void foo(int pos, Type1VO data) {
                if (pos >= 0 && pos < cases.size()) {
                    Intent intent = new Intent(MenuActivity.this, cases.get(pos).activityClz);
                    startActivity(intent);
                }
            }
        }));

        Collection<DataSet.Data> collection = new ArrayList<>();
        for (Foo foo : cases) {
            collection.add(new Type1VOImpl(foo.name));
        }

        dataSet.addAll(collection);
    }
}
