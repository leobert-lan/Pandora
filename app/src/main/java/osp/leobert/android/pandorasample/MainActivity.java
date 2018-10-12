package osp.leobert.android.pandorasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;

import osp.leobert.android.pandora.Pandora;
import osp.leobert.android.pandora.WrapperDataSet;
import osp.leobert.android.pandora.rv.DataSet;
import osp.leobert.android.pandora.rv.PandoraRealRvDataSet;
import osp.leobert.android.pandora.rv.PandoraWrapperRvDataSet;
import osp.leobert.android.pandorasample.dvh.Type1VH;
import osp.leobert.android.pandorasample.dvh.Type1VOImpl;
import osp.leobert.android.pandorasample.dvh.Type2VH;
import osp.leobert.android.pandorasample.dvh.Type2VOImpl;
import osp.leobert.android.pandorasample.dvh.Type3VH;
import osp.leobert.android.pandorasample.dvh.Type3VOImpl;
import osp.leobert.android.pandorasample.dvh.Type4VH;
import osp.leobert.android.pandorasample.dvh.Type4VOImpl;
import osp.leobert.android.pandorasample.dvh.Type5VH;
import osp.leobert.android.pandorasample.dvh.Type5VOImpl;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    PandoraWrapperRvDataSet<DataSet.Data> dataSet;

    PandoraRealRvDataSet<DataSet.Data> dataSetSection1;
    PandoraRealRvDataSet<DataSet.Data> dataSetSection2;
    PandoraRealRvDataSet<DataSet.Data> dataSetSection3;
    RvAdapter<PandoraWrapperRvDataSet<DataSet.Data>> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("lmsg", "test");

        recyclerView = findViewById(R.id.rv);
        initDataSet();

        adapter = new RvAdapter<>(dataSet);
        Pandora.bind2RecyclerViewAdapter(dataSet.getDataSet(), adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.color.colorAccent));
        recyclerView.addItemDecoration(decoration);

        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSection1();
            }
        });

        findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSection2();
            }
        });

        findViewById(R.id.b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSection3();
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
        dataSetSection3 = new PandoraRealRvDataSet<>(Pandora.<DataSet.Data>real());
        dataSetSection3.setAlias("sec3");
        dataSet.addSub(dataSetSection1.getRealDataSet());
        dataSet.addSub(dataSetSection2.getRealDataSet());
        dataSet.addSub(dataSetSection3.getRealDataSet());

        dataSet.registerDVRelation(Type1VOImpl.class, new Type1VH.Creator(new Type1VH.ItemInteract() {
            @Override
            public void foo() {

            }
        }));

        dataSet.registerDVRelation(Type2VOImpl.class, new Type2VH.Creator(null));
        dataSet.registerDVRelation(Type3VOImpl.class, new Type3VH.Creator(null));
        dataSet.registerDVRelation(Type4VOImpl.class, new Type4VH.Creator(null));
        dataSet.registerDVRelation(Type5VOImpl.class, new Type5VH.Creator(null));
    }

    private void addSection1() {
        Collection<DataSet.Data> collection = new ArrayList<>();
        collection.add(new Type1VOImpl("section1[5423]" + TimeUtil.getCurrentTimeInString()));
        collection.add(new Type5VOImpl(2));
        collection.add(new Type4VOImpl(3));
        collection.add(new Type2VOImpl(4));
        collection.add(new Type3VOImpl(5));

        dataSetSection1.addAll(collection);
    }

    private void addSection2() {
        Collection<DataSet.Data> collection = new ArrayList<>();
        collection.add(new Type1VOImpl("section2[53422]" + TimeUtil.getCurrentTimeInString()));
        collection.add(new Type5VOImpl(2));
        collection.add(new Type3VOImpl(3));
        collection.add(new Type4VOImpl(4));
        collection.add(new Type2VOImpl(5));
        collection.add(new Type2VOImpl(6));

        dataSetSection2.addAll(collection);
    }

    private void addSection3() {
        Collection<DataSet.Data> collection = new ArrayList<>();
        collection.add(new Type1VOImpl("section3[5455]" + TimeUtil.getCurrentTimeInString()));
        collection.add(new Type5VOImpl(2));
        collection.add(new Type4VOImpl(3));
        collection.add(new Type5VOImpl(4));
        collection.add(new Type5VOImpl(5));

        dataSetSection3.addAll(collection);
    }
}
