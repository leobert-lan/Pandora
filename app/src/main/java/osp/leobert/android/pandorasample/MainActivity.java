package osp.leobert.android.pandorasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import osp.leobert.android.pandora.Pandora;
import osp.leobert.android.pandora.WrapperDataSet;
import osp.leobert.android.pandorasample.dvh.Type1VH;
import osp.leobert.android.pandorasample.dvh.Type1VOImpl;
import osp.leobert.android.pandorasample.dvh.Type2VH;
import osp.leobert.android.pandorasample.dvh.Type2VOImpl;
import osp.leobert.android.pandorasample.dvh.Type3VH;
import osp.leobert.android.pandorasample.dvh.Type3VOImpl;
import osp.leobert.android.pandorasample.dvh.Type4VH;
import osp.leobert.android.pandorasample.dvh.Type4VOImpl;
import osp.leobert.android.pandorasample.dvh.Type5VH;
import osp.leobert.android.pandorasample.dvh.Type5VO;
import osp.leobert.android.pandorasample.dvh.Type5VOImpl;
import osp.leobert.android.pandorasample.nds.DateVhMappingPool;
import osp.leobert.android.pandorasample.nds.PandoraRealRvDataSet;
import osp.leobert.android.pandorasample.nds.PandoraWrapperRvDataSet;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    //DataSet.Data<?,AbsViewHolder<?>>
    PandoraWrapperRvDataSet dataSet;

    PandoraRealRvDataSet<? extends DataSet.Data> dataSetSection1;
    PandoraRealRvDataSet<? extends DataSet.Data> dataSetSection2;
    PandoraRealRvDataSet<? extends Type5VO> dataSetSection3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
    }

    private void initDataSet() {
        WrapperDataSet<DataSet.Data> wrapperDataSet = Pandora.wrapper();
        dataSet = new PandoraWrapperRvDataSet<>(wrapperDataSet);

        dataSetSection1 = new PandoraRealRvDataSet<>(Pandora.<DataSet.Data>real());
        dataSetSection2 = new PandoraRealRvDataSet<>(Pandora.<DataSet.Data>real());
        dataSetSection3 = new PandoraRealRvDataSet<>(Pandora.<Type5VO>real());
        dataSet.addSub(dataSetSection1.getRealDataSet());
        dataSet.addSub(dataSetSection2.getRealDataSet());
        dataSet.addSub(dataSetSection3.getRealDataSet());

        dataSet.registerDVRelation(Type1VOImpl.class,new Type1VH.Creator(new Type1VH.ItemInteract() {
            @Override
            public void foo() {

            }
        }));

        dataSet.registerDVRelation(Type2VOImpl.class,new Type2VH.Creator(null));
        dataSet.registerDVRelation(Type3VOImpl.class,new Type3VH.Creator(null));
        dataSet.registerDVRelation(Type4VOImpl.class,new Type4VH.Creator(null));
        dataSet.registerDVRelation(Type5VOImpl.class,new Type5VH.Creator(null));
    }
}
