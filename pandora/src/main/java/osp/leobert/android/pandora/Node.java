package osp.leobert.android.pandora;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> Node </p>
 * <p><b>Description:</b> node in a tree </p>
 * Created by leobert on 2018/9/29.
 */
public interface Node<T> {
    int NO_GROUP_INDEX = -1;

    /**
     * @return the index of itself in the generation
     */
    int getGroupIndex();

    /**
     * @param sub sub node to be bind
     */
    void addSub(T sub);

    /**
     * @return true if it has been bind to one parent node
     */
    boolean hasBind2Parent();

    /**
     * remove this node from it's parent node,use {@link #hasBind2Parent()} to check if it has bind to one parent node
     */
    void removeFromOriginalParent();

    /**
     * @param sub
     */
    void removeSub(T sub);
}
