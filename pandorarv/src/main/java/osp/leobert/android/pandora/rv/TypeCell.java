package osp.leobert.android.pandora.rv;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> TypeCell </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2018/10/10.
 */
public final class TypeCell<T> {
    @IntRange(from = 0)
    private final int index;

    @IntRange(from = 1)
    private int maxSize;

    public void updateMaxSize(@IntRange(from = 1) int maxSize) {
        this.maxSize = maxSize;
    }

    @NonNull
    private final DateVhMappingPool.DVRelation<T> dvRelation;

    private final List<String> subTypeTokens;

    public boolean workFor(@NonNull String clz) {
        return clz.equals(dvRelation.getDataClz().getName());
    }

    public TypeCell(@IntRange(from = 0) int index, @NonNull DateVhMappingPool.DVRelation<T> dvRelation) {
        this.index = index;
        this.dvRelation = dvRelation;
        subTypeTokens = new ArrayList<>();
    }

    public int getSubTypeCount() {
        return dvRelation.one2N();
    }

    public int getItemViewType(T data) {
//            dvRelation.getDataClz().equals(data.getClass()) 暂不做校验
        String token = dvRelation.subTypeToken(data);
        if (!subTypeTokens.contains(token)) {
            subTypeTokens.add(token);
        }
        int index = subTypeTokens.indexOf(token);
        return this.index * maxSize + index;
    }

    public ViewHolderCreator getVhCreator(int subTypeIndex) {
        String subTypeToken = subTypeTokens.get(subTypeIndex);
        return dvRelation.getVhCreator(subTypeToken);
    }
}
