# Pandora
Pandora is a library to build flexible dataset.

公司项目中上一次技术升级我新写了一套复杂数据集方案以及RecyclerView多样式方案，相比于其他类似的多样式支持项目，其侵入性较低、
对其他从RecyclerView以及Adapter入手的的项目而言，原理上来说可以无缝兼容。

现将其脱敏后开源，欢迎star以及提供改进建议。

*recyclerview多样式的辅助插件会独立项目*

[插件项目Pandora-Plugin地址](https://github.com/leobert-lan/Pandora-Plugin)

## 关于Pandora
首先为公司产品做一下简单推广，笔者目前在为[“哈罗摩托”](https://sj.qq.com/myapp/detail.htm?apkName=com.jdd.motorfans)团队提供技术服务。哈罗摩托汇聚摩托车爱好者，源自国内具影响力的摩托社区！有兴趣的朋友可以试着玩玩（偶尔会有小福利😂）

在之前半年里，我们产品主要朝**资讯方向**发展。而限于H5人力有限，绝大多数的内容均由原生实现，**粗略评估有超过80%的页面都可以使用RecyclerView做呈现**。当时笔者开发了一套支持多样式的RecyclerView框架支持以及结合实际情况开发了复杂数据集方案。

### 为什么不使用已有的框架
在当时，确实有非常优秀的框架，例如专注于多样式的：drakeet的[MultiType](https://github.com/drakeet/MultiType),sockeqwe的[AdapterDelegates](https://github.com/sockeqwe/AdapterDelegates)；专注于特定模板的：alibaba的[Vlayout](https://github.com/alibaba/vlayout)等等。

1. 首先是需求，当时主要的场景是混杂的样式（列表）、富文本的编辑与显示，这一点就决定了类似于VLayout的实现预定义模板布局的框架都是不符合需求的。
2. 当时，项目非常的混乱，受“快速迭代”，“能用就行”等瞎几把鬼扯的指（遭）导（受）性（压）思（迫）想，RecyclerView相关功能的设计是急躁的，基类的定义都比较随意，扩展性已经比较差，想要直接引入一些库直接使用基本没有希望（基本都有Adapter、ViewHolder的基类）


## Module简介

* APP:sample项目
* pandora：一个用于快速构建复杂内容数据集的框架，抽象了数据集的构建，封装了数据集的常见操作，**可以将多个数据集构建为树**。*PS:受限于时间，哈罗摩托项目中，这一块是根据实际需求去做的，没有做抽象和封装，可以说当前是新设计的，可能存在各种坑*
* pandorarv：RecyclerView多样式支持库。


## Pandora适合哪些项目
笼统来说，MVC模式和MVP模式可以无缝使用，MVVM模式的项目可能需要做一定的调整。业务中不牵涉到多组别数据的基本没有相关需求（例如地图类应用）。

对于电商类、资讯内容类的应用比较适合。

## 集成

*jfrog近期好像做了一些调整，path需要三个部分，request被拒了，在成功发布后我会修改本内容，请先使用以下仓库地址*

```
 maven {
            url 'https://dl.bintray.com/leobert-lan-oss/maven/'
}
```



library|pandora|pandorarv
---|---|---
最新版本|[![Download](https://api.bintray.com/packages/leobert-lan-oss/maven/pandora/images/download.svg)](https://api.bintray.com/packages/leobert-lan-oss/maven/pandora/_latestVersion)|[![Download](https://api.bintray.com/packages/leobert-lan-oss/maven/pandorarv/images/download.svg)](https://bintray.com/leobert-lan-oss/maven/pandorarv/_latestVersion)

``` gradle
implementation或compile 'osp.leobert.android:pandora:{version}'
implementation或compile 'osp.leobert.android:pandorarv:{version}'

```

## 使用简介
### pandora

前面我们提到pandora是用于构建复杂数据集的。先岔开一句，常见的需求中，无非：

* 一个独立的数据。 one instance of a Data Object.
* 一组类型一致的数据。 one collection of one Data Object's instances 
* 由以上两种组合成的更大的数据集

而且一组类型一致的数据往往是有序的。

我们还没有针对独立的数据做什么工作，目前（截至version 0.0.3）依旧是针对一组类型一致的数据及其组合而做的设计。更具体的设计分析我会在个人博客更新，并同步地址到wiki。

**前面我们提到可以将多组数据集构建成树**

而树中的节点，任意的最底层节点一定是RealDataSet(或其子类)，其他的都是WrapperDataSet(或其子类)。

只有RealDataSet能“真正的包含数据”，WrapperDataSet可以包含WrapperDataSet或者RealDataSet作为其子节点。

创建一个RealDataSet：

```
//直接实例化
RealDataSet<T> foo = new RealDataSet<>();

//或者使用工厂
//osp.leobert.android.pandora.Pandora#real():

 public static <T> RealDataSet<T> real() {
        return new RealDataSet<>();
 }

```

创建一个WrapperDataSet:

```
//直接实例化
WrapperDataSet<T> foo = new WrapperDataSet<>();

//或者使用工厂
//osp.leobert.android.pandora.Pandora#wrapper():

 public static <T> WrapperDataSet<T> wrapper() {
        return new WrapperDataSet<>();
 }

```

**注意去泛化**

从这里大家应该可以直接推测出，想要使用复杂数据集时，去泛化只会使用抽象类型。

**这恰好是我们使用Pandora作为项目名的隐喻，你不知道会释放出什么，但取出来的东西一定会被安排的明明白白😂，这里会在pandorarv的使用以及我的博客中做一定的延伸**

基础数据操作API，顾名思义

```
int getDataCount();

T getDataByIndex(int index);

void clearAllData();

void add(T item);

void add(int pos, T item);

void addAll(Collection<T> collection);

void remove(Object item);

void removeAtPos(int position);

void setData(Collection<T> collection);
```

对于一个节点数据集的基础API：

```
 /**
 * @return the index of itself in the generation
 */
int getGroupIndex();

/**
 * add a child node
 * @param sub sub node to be bind
 */
void addChild(T sub);

/**
 * @return true if it has been bind to one parent node
 */
boolean hasBind2Parent();

/**
 * remove this node from it's parent node,use {@link #hasBind2Parent()} to check if it has bind to one parent node
 */
void removeFromOriginalParent();

/**
 * @param sub child node to be removed
 */
void removeChild(T sub);
```
#### 关于数据变化：
pandora数据集中，数据的变化是通过DiffUtil去计算的。

在 areItemsTheSame中主要依靠equals来判断是否一致；
在 areContentsTheSame中，因为我们哈罗摩托项目的一些要求，我们同时判断了equals和hash。具体请参见代码。

#### 关于transaction:
pandora中并没有实现真正意义的transaction，因为它无法支持一旦失败全部取消，他也无法支持锁机制（而且本身pandora也没有去考虑多线程）
它存在的意义只是减少一些没有意义的刷新操作，举个例子：在异步获取一组数据的场景下，先用一个数据Foo代表占位视图，在获取到这一组数据时，先移除Foo，再在尾部追加这一组数据。 如果先移除再追加，会带来两次变化的计算，如果使用transaction，

```
//伪代码：
dataset.startTransaction();
dataset.remove(foo);
dataset.append(list);
dataset.endTransaction();
```

可以减少一些计算.


### pandorarv
pandorarv 是pandora配合recyclerview使用的，并且提供了一套无侵入性的多样式支持；

提供给RecyclerView.Adapter使用的数据集为`osp.leobert.android.pandora.rv.DataSet`
library中提供了pandora的配套实现：

* PandoraWrapperRvDataSet
* PandoraRealRvDataSet

我们支持了 VO interface 到 ViewHolder的一对多映射。

我们定义VO interface时需要继承 `interface osp.leobert.android.pandora.rv.DataSet.Data`
定义ViewHolder时需要实现 `interface: osp.leobert.android.pandora.rv.IViewHolder`
*这样不会影响原有项目中存在的继承结构*

绑定VO 和 VH的关系通过以下形式：

* `osp.leobert.android.pandora.rv.DataSet#registerDVRelation(java.lang.Class<?>, osp.leobert.android.pandora.rv.ViewHolderCreator)`
* `osp.leobert.android.pandora.rv.DataSet#registerDvRelation(DateVhMappingPool.DVRelation... dvRelations)`
* `osp.leobert.android.pandora.rv.DataSet#registerDVRelation(osp.leobert.android.pandora.rv.DateVhMappingPool.DVRelation<?>)`

代码示例：
基类VH，按照你的项目实际处理；

```
public abstract class AbsViewHolder2<T> extends BaseRecyclerViewHolder implements IViewHolder<T> {
    public AbsViewHolder2(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public RecyclerView.ViewHolder asViewHolder() {
        return this;
    }

    protected Context getContext() {
        return itemView.getContext();
    }


    @Override
    public void onViewAttachedToWindow() {

    }

    @Override
    public void onViewDetachedFromWindow() {

    }
}
```


定义VH：

```
public class FooVH2 extends AbsViewHolder2<FooVO2> {
        private final ItemInteract mItemInteract;

        private FooVO2 mData;


        public FooVH2(View itemView, ItemInteract itemInteract) {
            super(itemView);
            this.mItemInteract = itemInteract;
            //TODO: find views and bind actions here
        }

        @Override
        public void setData(FooVO2 data) {
            mData = data;
            //TODO: bind data to views

        }

        public static final class Creator extends ViewHolderCreator {
            private final
            ItemInteract itemInteract;

            public Creator(ItemInteract itemInteract) {
                this.itemInteract = itemInteract;
            }

            @Override
            public AbsViewHolder2<FooVO2> createViewHolder(ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.app_vh_foo, parent, false);
                return new FooVH2(view, itemInteract);
            }
        }

        public interface ItemInteract {
            //TODO: define your actions here
        }
}
```

定义VO

```
public static interface FooVO2 extends DataSet.Data<DataSet.Data, AbsViewHolder2<DataSet.Data>> {
	String foo();
}
```

假定的实现类：FooVO2Impl:

```
public class FooVO2Impl implement FooVO2{
	
	String foo;

	public String foo() {
		return foo;
	}

	@Override
    public void setToViewHolder(AbsViewHolder2<DataSet.Data> viewHolder) {
    	//适当的判断或者一些前置逻辑 ...
    	
    	//设置数据
       viewHolder.setData(this);
    }
}
```


绑定关系：

```
dataset.registerDVRelation(FooVO2Impl.class, new FooVH2.Creator(null));
```

这样，如果数据集中存在一个FooVO2Impl的实例（注意，他的子类不会被考虑）就使用FooVH2;

一对多：
当出现一对多场景时，假定产品定义了，当FooVO2Impl#foo()为null时，展现另一种样式的VH：
`public class FooBarVH extends AbsViewHolder2<FooVO2> `

```
 dataSet.registerDVRelation(new DateVhMappingPool.DVRelation<FooVO2Impl>() {
            @Override
            public Class<FooVO2Impl> getDataClz() {
                return FooVO2Impl.class;
            }

            @Override
            public int one2N() {
                return 2;
            }

            @Override
            public String subTypeToken(@NonNull FooVO2Impl data) {
                if (data.foo == null)
                    return "style1";
                return "style2";
            }

            @Override
            public oViewHolderCreator getVhCreator(@NonNull String subTypeToken) {
                if ("style1".equals(subTypeToken))
                    return new FooBarVH.Creator(null);
                return new FooVH2.Creator(null);
            }
        });
```


#### 支持向RecyclerView应用更新：

一定要调用以下方法实现绑定：
```
osp.leobert.android.pandora.Pandora#bind2RecyclerViewAdapter
```

更多内容请参照sample或者阅读源码

欢迎star或者提issue

## License

[MIT](https://github.com/leobert-lan/Pandora/blob/2b2f7dfe709f49ada3def69e2d68046eae65b7d5/LICENSE)

























