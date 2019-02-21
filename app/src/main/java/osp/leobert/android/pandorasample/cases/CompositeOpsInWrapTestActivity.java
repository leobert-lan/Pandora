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

package osp.leobert.android.pandorasample.cases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import osp.leobert.android.pandora.Pandora;
import osp.leobert.android.pandora.WrapperDataSet;
import osp.leobert.android.pandora.rv.DataSet;
import osp.leobert.android.pandora.rv.PandoraRealRvDataSet;
import osp.leobert.android.pandora.rv.PandoraWrapperRvDataSet;
import osp.leobert.android.pandorasample.R;
import osp.leobert.android.pandorasample.RvAdapter;
import osp.leobert.android.pandorasample.dvh.Type1VH;
import osp.leobert.android.pandorasample.dvh.Type1VO;
import osp.leobert.android.pandorasample.dvh.Type1VOImpl;
import osp.leobert.android.pandorasample.dvh.Type2VH;
import osp.leobert.android.pandorasample.dvh.Type2VOImpl;

/**
 * Wrap数据集中的组合操作测试
 * demo：删除所有的Type2Impl数据
 */
public class CompositeOpsInWrapTestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    PandoraWrapperRvDataSet<DataSet.Data> dataSet;

    PandoraRealRvDataSet<DataSet.Data> dataSetSection1;
    PandoraRealRvDataSet<DataSet.Data> dataSetSection2;
    RvAdapter<PandoraWrapperRvDataSet<DataSet.Data>> adapter;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        initDataSet();

        adapter = new RvAdapter<>(dataSet);
        Pandora.bind2RecyclerViewAdapter(dataSet.getDataSet(), adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.color.colorAccent));
        recyclerView.addItemDecoration(decoration);

        Button b1 = findViewById(R.id.b1);
        b1.setText("正确演示1");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompositeOpsInWrapTestActivity.this, "效率略低", Toast.LENGTH_SHORT).show();
                demo1();
            }
        });

        Button b2 = findViewById(R.id.b2);
        b2.setText("错误演示1");
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompositeOpsInWrapTestActivity.this, "错误的延迟操作", Toast.LENGTH_SHORT).show();
                errorDemo1();
            }
        });

        Button b3 = findViewById(R.id.b3);
        b3.setText("随机修改属性");
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProperty();
            }
        });

    }

    private void initDataSet() {
        WrapperDataSet<DataSet.Data> wrapperDataSet = Pandora.wrapper();
        dataSet = new PandoraWrapperRvDataSet<>(wrapperDataSet);

        dataSetSection1 = new PandoraRealRvDataSet<>(Pandora.<DataSet.Data>real());
        dataSetSection1.setAlias("sec1");
        dataSetSection2 = new PandoraRealRvDataSet<>(Pandora.<DataSet.Data>real());
        dataSetSection2.setAlias("sec2");
        dataSet.addSub(dataSetSection1.getRealDataSet());
        dataSet.addSub(dataSetSection2.getRealDataSet());

        dataSet.registerDVRelation(Type1VOImpl.class, new Type1VH.Creator(new Type1VH.ItemInteract() {
            @Override
            public void foo(int pos, Type1VO data) {
            }
        }));

        dataSet.registerDVRelation(Type2VOImpl.class, new Type2VH.Creator(null));
//        dataSet.registerDVRelation(Type3VOImpl.class, new Type3VH.Creator(null));
//        dataSet.registerDVRelation(Type4VOImpl.class, new Type4VH.Creator(null));
//        dataSet.registerDVRelation(Type5VOImpl.class, new Type5VH.Creator(null));

        initData();
    }

    private void initData() {
        Collection<DataSet.Data> collection1 = new ArrayList<>();
        collection1.add(new Type1VOImpl("s1-" + generateIndex()));
        collection1.add(new Type2VOImpl(1));
        collection1.add(new Type2VOImpl(2));
        collection1.add(new Type1VOImpl("s1-" + generateIndex()));
        dataSetSection1.addAll(collection1);

        Collection<DataSet.Data> collection2 = new ArrayList<>();
        collection2.add(new Type1VOImpl("s2-" + generateIndex()));
        collection2.add(new Type1VOImpl("s2-" + generateIndex()));
        collection2.add(new Type2VOImpl(3));
        dataSetSection2.addAll(collection2);

        if (!dataSetSection2.hasBind2Parent()) {
            dataSet.startTransaction();
            dataSet.addSub(dataSetSection2.getRealDataSet());
            dataSet.endTransaction();
        }
    }

    /**
     * 暴力方式，每次执行一步数据变动都重新建树（removeAtPos内部执行了）
     */
    private void demo1() {
        int count = dataSet.getCount();
        for (int i = 0; i < count; i++) {
            DataSet.Data data = dataSet.getDataByIndex(i);
            if (data instanceof Type2VOImpl) {
                dataSet.removeAtPos(i);
                i--;
            }
        }
    }

    private String generateIndex() {
        String ret = String.valueOf(index);
        index++;
        return ret;
    }

    private void errorDemo1() {
        try {
            List<Integer> targets = new ArrayList<>();
            int count = dataSet.getCount();
            for (int i = 0; i < count; i++) {
                DataSet.Data data = dataSet.getDataByIndex(i);
                if (data instanceof Type2VOImpl) {
                    targets.add(i);
                }
            }

            dataSet.startTransaction();
            Integer integer;
            while ((integer = targets.remove(0)) != null) {
                dataSet.removeAtPos(integer); //这里内部已经重新建树，即时没有，也会遇到问题，经历过数据变动后，原有的index已不再可靠
                                              //无论是否重建树，根据index取sub node的算法决定了一旦有元素数量变动，
                                              // 使用原有index作为入参无法可靠的得到原来的sub node
            }
            dataSet.endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void changeProperty() {

    }
}
