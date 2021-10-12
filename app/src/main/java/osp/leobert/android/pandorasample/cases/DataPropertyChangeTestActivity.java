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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import osp.leobert.android.pandora.Pandora;
import osp.leobert.android.pandora.WrapperDataSet;
import osp.leobert.android.pandora.rv.DataSet;
import osp.leobert.android.pandora.rv.PandoraRealRvDataSet;
import osp.leobert.android.pandora.rv.PandoraWrapperRvDataSet;
import osp.leobert.android.pandora.visitor.TypeVisitor;
import osp.leobert.android.pandorasample.R;
import osp.leobert.android.pandorasample.RvAdapter;
import osp.leobert.android.pandorasample.dvh.Type1VH;
import osp.leobert.android.pandorasample.dvh.Type1VO;
import osp.leobert.android.pandorasample.dvh.Type1VOImpl;

/**
 * 属性变化测试
 */
public class DataPropertyChangeTestActivity extends AppCompatActivity {

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
        b1.setText("sec1添加数据");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSection1();
            }
        });

        Button b2 = findViewById(R.id.b2);
        b2.setText("sec2添加数据");
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSection2();
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
                dataSet.startTransaction();
                data.resetData("reset - " + data.getData());
                dataSet.endTransaction(); //test
            }
        }));

//        dataSet.registerDVRelation(Type2VOImpl.class, new Type2VH.Creator(null));
//        dataSet.registerDVRelation(Type3VOImpl.class, new Type3VH.Creator(null));
//        dataSet.registerDVRelation(Type4VOImpl.class, new Type4VH.Creator(null));
//        dataSet.registerDVRelation(Type5VOImpl.class, new Type5VH.Creator(null));
    }

    private void addSection1() {
        Collection<DataSet.Data> collection = new ArrayList<>();
        collection.add(new Type1VOImpl("s1-" + generateIndex()));
        dataSetSection1.addAll(collection);
    }

    private String generateIndex() {
        String ret = String.valueOf(index);
        index++;
        return ret;
    }

    private void addSection2() {
        Collection<DataSet.Data> collection = new ArrayList<>();
        collection.add(new Type1VOImpl("s2-" + generateIndex()));
        collection.add(new Type1VOImpl("s2-" + generateIndex()));
        dataSetSection2.addAll(collection);

        if (!dataSetSection2.hasBind2Parent()) {
            dataSet.startTransaction();
            dataSet.addSub(dataSetSection2.getRealDataSet());
            dataSet.endTransaction();
        }
    }

    private void changeProperty() {
        final long ts = System.currentTimeMillis();
        int count = dataSet.getCount();
        final int target = (int) (ts % count);

        //normal sample:
//        DataSet.Data data = dataSet.getDataByIndex(target);
//        if (data instanceof Type1VO) {
//            dataSet.startTransaction();
//            ((Type1VO) data).resetData("random change " + target + "  " + ts);
//            dataSet.endTransaction();
//        }

        dataSet.getDataSet().accept(target, new TypeVisitor<Type1VO>(Type1VO.class) {

            @Override
            public void onHit(Type1VO element) {
                dataSet.startTransaction();
                Toast.makeText(DataPropertyChangeTestActivity.this, "修改position：" + target, Toast.LENGTH_SHORT).show();
                element.resetData("random change " + target + "  " + ts);
                dataSet.endTransaction();
            }
        });
    }
}
