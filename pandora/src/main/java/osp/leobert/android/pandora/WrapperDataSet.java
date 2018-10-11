package osp.leobert.android.pandora;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p><b>Package:</b> osp.leobert.android.pandora </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> WrapperDataSet </p>
 * <p><b>Description:</b> a wrapper contains any PandoraBoxAdapter </p>
 * Created by leobert on 2018/9/29.
 */
public class WrapperDataSet<T> extends PandoraBoxAdapter<T> {
    private void log(int p, String msg) {
        Log.println(p, "WrapperDataSet", msg);
    }

    private final List<T> oldList = new ArrayList<>();
    private boolean useTransaction = false;
    private final DiffUtil.Callback diffCallback = new DiffUtil.Callback() {
        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return getDataCount();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldItemPosition >= oldList.size()) return false;
            if (newItemPosition >= getDataCount()) return false;

            T old = oldList.get(oldItemPosition);
            T now = getDataByIndex(newItemPosition);
            if (old == null) return now == null;
            else return old.equals(now);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldItemPosition >= oldList.size())
                return false;
            if (newItemPosition >= getDataCount())
                return false;

            T old = oldList.get(oldItemPosition);
            T now = getDataByIndex(newItemPosition);
            if (old == null)
                return now == null;
            else
                return old.equals(now);
        }
    };

    private int groupIndex;// = NO_GROUP_INDEX;
    private int startIndex;

    @Nullable
    private PandoraBoxAdapter<T> parent;

    private final List<PandoraBoxAdapter<T>> subs = new ArrayList<>();

    public WrapperDataSet(int groupIndex, int startIndex) {
        this.startIndex = startIndex;
        this.groupIndex = groupIndex;
    }

    public WrapperDataSet() {
        this.startIndex = 0;
        this.groupIndex = NO_GROUP_INDEX;
    }

    @Override
    public int getStartIndex() {
        return startIndex;
    }

    @Override
    protected void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public int getGroupIndex() {
        return groupIndex;
    }

    @Override
    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    @Override
    protected void hasAddToParent(@NonNull PandoraBoxAdapter<T> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(PandoraBoxAdapter<T> sub) {
        if (sub == null)
            return;

        if (sub.hasBind2Parent()) {
            sub.removeFromOriginalParent();
        }

        int groupIndex = subs.size();
        sub.setGroupIndex(groupIndex);

        long count = getDataCount();
        sub.setStartIndex((int) count);
        sub.hasAddToParent(this);
        subs.add(sub);
    }


    @Override
    public boolean hasBind2Parent() {
        return parent != null;
    }

    @Override
    public void removeFromOriginalParent() {
        if (parent != null) {
            parent.removeChild(this);
            parent = null;
        }
    }

    @Override
    public void removeChild(PandoraBoxAdapter<T> sub) {
        if (subs.contains(sub)) {
            onBeforeChanged();
            subs.remove(sub);
            onAfterChanged();
        }
    }

    @Override
    public final int getDataCount() {
        int ret = 0;
        for (PandoraBoxAdapter subAdapter : subs) {
            if (subAdapter == null) continue;
            ret += subAdapter.getDataCount();
        }
        return ret;
    }

    @Override
    @Nullable
    public final T getDataByIndex(int index) {
        log(Log.DEBUG, "getDataByIndex" + index);
        if (startIndex <= index && startIndex + getDataCount() > index) {
            //find the sub

            PandoraBoxAdapter<T> targetSub = null;

            int mid = subs.size() / 2;
            PandoraBoxAdapter<T> sub = subs.get(mid);
            if (index >= sub.getStartIndex() && index < (sub.getStartIndex() + sub.getDataCount())) {
                targetSub = sub;
            }

            int start = 0;
            int end = subs.size() - 1;
            while (start <= end) {
                mid = (end - start) / 2 + start;
                PandoraBoxAdapter<T> adapter = subs.get(mid);

                if (index < adapter.getStartIndex()) {
                    end = mid - 1;
                } else if (adapter.getDataCount() == 0 || index > (adapter.getStartIndex() + adapter.getDataCount())) {
                    start = mid + 1;
                } else {
                    targetSub = adapter;
                    break;
                }
            }
            if (targetSub == null) {
                log(Log.ERROR, "getDataByIndex" + index + ";no child find");
                return null;
            }

            log(Log.DEBUG, "getDataByIndex" + index + targetSub.getAlias() + " - " + targetSub.toString());

            int resolvedIndex = index - targetSub.getStartIndex();

            return targetSub.getDataByIndex(resolvedIndex);
        }
        return null;
    }

    @Override
    public void clearAllData() {
        startTransaction();
        for (PandoraBoxAdapter<T> sub : subs) {
            if (sub == null) continue;
            sub.clearAllData();
        }
        endTransaction();
    }

    @Override
    public void add(T item) {
        startTransaction();
        if (subs.size() > 0) {
            subs.get(subs.size() - 1).add(item);
        }
        endTransaction();
    }

    @Override
    public void add(int pos, T item) {
        if (pos < 0)
            return;
        startTransaction();
        if (pos >= getDataCount()) { //append at the last
            add(item);
        } else {
            Pair<PandoraBoxAdapter<T>, Integer> target = retrieveAdapterByDataIndex2(pos);

            if (target == null)
                log(Log.ERROR, "bug,cannot find target adapter");
            else {
                target.first.add(target.second, item);
            }
        }

        endTransaction();
    }

    @Override
    public void addAll(Collection<T> collection) {
        startTransaction();
        if (subs.size() > 0) {
            subs.get(subs.size() - 1).addAll(collection);
        }
        endTransaction();
    }

    @Override
    public void remove(Object item) {
        startTransaction();
        for (PandoraBoxAdapter<T> sub : subs) {
            if (sub == null) continue;
            try {
                sub.remove(item); // safety
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        endTransaction();
    }

    @Override
    public void removeAtPos(int position) {
        startTransaction();
        if (position < 0 || position >= getDataCount()) {
            log(Log.ERROR, "index out of boundary");
        } else {
            Pair<PandoraBoxAdapter<T>, Integer> target = retrieveAdapterByDataIndex2(position);
            if (target == null)
                log(Log.ERROR, "bug,cannot find target adapter");
            else {
                target.first.remove(target.second);
            }
        }
        endTransaction();
    }

    @Override
    public void setData(Collection<T> collection) {
        //no-op
    }

    @Override
    protected final void onBeforeChanged() {
        if (!inTransaction())
            snapshot();

        if (parent != null) {
            parent.onBeforeChanged();
        }
    }

    @Override
    protected final void onAfterChanged() {
        rebuildSubNodes();
        if (parent != null)
            parent.onAfterChanged();
        if (!inTransaction())
            calcChangeAndNotify();

    }

    private void calcChangeAndNotify() {
        if (listUpdateCallback != null) {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
            result.dispatchUpdatesTo(listUpdateCallback);
        }
    }

    private void rebuildSubNodes() {
        int subCounts = subs.size();
        int offset = 0;
        for (int i = 0; i < subCounts; i++) {
            PandoraBoxAdapter<T> sub = subs.get(i);
            if (sub == null) continue;

            sub.setGroupIndex(i);
            sub.setStartIndex(offset);
            offset += sub.getDataCount();
        }
    }


    private void snapshot() {
        oldList.clear();
        long count = getDataCount();
        for (int i = 0; i < count; i++) {
            oldList.add(i, getDataByIndex(i));
        }
    }


    @Override
    protected boolean inTransaction() {
        return useTransaction || isParentInTransaction();
    }

    private boolean isParentInTransaction() {
        if (parent != null)
            return parent.inTransaction();
        return false;
    }

    @Override
    public void startTransaction() {
        useTransaction = true;
        snapshot();
    }

    @Override
    public void endTransaction() {
        useTransaction = false;
        calcChangeAndNotify();
    }

    @Override
    public void endTransactionSilently() {
        useTransaction = false;
    }

    @Override
    @CheckResult
    @Nullable
    public PandoraBoxAdapter<T> retrieveAdapterByDataIndex(int index) {
        Pair<PandoraBoxAdapter<T>, Integer> temp = retrieveAdapterByDataIndex2(index);
        if (temp == null)
            return null;
        return temp.first;
    /*    if (startIndex <= index && startIndex + getDataCount() > index) {
            //find the sub

            PandoraBoxAdapter<T> targetSub = null;

            int mid = subs.size() / 2;
            PandoraBoxAdapter<T> sub = subs.get(mid);
            if (index >= sub.getStartIndex() && index < (sub.getStartIndex() + sub.getDataCount())) {
                targetSub = sub;
            }

            int start = 0;
            int end = subs.size() - 1;
            while (start <= end) {
                mid = (end - start) / 2 + start;
                PandoraBoxAdapter<T> adapter = subs.get(mid);

                if (index < adapter.getStartIndex()) {
                    end = mid - 1;
                } else if (index > (adapter.getStartIndex() + adapter.getDataCount())) {
                    start = mid + 1;
                } else {
                    targetSub = adapter;
                    break;
                }
            }

            if (targetSub == null)
                return null;

            int resolvedIndex = index - targetSub.getStartIndex();

            return targetSub.retrieveAdapterByDataIndex(resolvedIndex);
        }
        return null;*/
    }

    @Nullable
    @Override
    public Pair<PandoraBoxAdapter<T>, Integer> retrieveAdapterByDataIndex2(int index) {
        if (startIndex <= index && startIndex + getDataCount() > index) {
            //find the sub

            PandoraBoxAdapter<T> targetSub = null;

            int mid = subs.size() / 2;
            PandoraBoxAdapter<T> sub = subs.get(mid);
            if (index >= sub.getStartIndex() && index < (sub.getStartIndex() + sub.getDataCount())) {
                targetSub = sub;
            }

            int start = 0;
            int end = subs.size() - 1;
            while (start <= end) {
                mid = (end - start) / 2 + start;
                PandoraBoxAdapter<T> adapter = subs.get(mid);

                if (index < adapter.getStartIndex()) {
                    end = mid - 1;
                } else if (adapter.getDataCount() == 0 || index > (adapter.getStartIndex() + adapter.getDataCount())) {
                    start = mid + 1;
                } else {
                    targetSub = adapter;
                    break;
                }
            }

            if (targetSub == null)
                return null;

            int resolvedIndex = index - targetSub.getStartIndex();

            return Pair.create(targetSub.retrieveAdapterByDataIndex(resolvedIndex), resolvedIndex);
        }
        return null;
    }
}
