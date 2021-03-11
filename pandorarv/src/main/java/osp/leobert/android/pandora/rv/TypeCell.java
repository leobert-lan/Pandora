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

package osp.leobert.android.pandora.rv;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>Package:</b> osp.leobert.android.pandorarv </p>
 * <p><b>Project:</b> Pandorarv </p>
 * <p><b>Classname:</b> TypeCell </p>
 * <p><b>Description:</b> model for a type of item used in RecyclerView </p>
 * Created by leobert on 2018/10/10.
 */
/*public*/ final class TypeCell<T> {

    static <T> TypeCell<T> of(int index, TypeCell<T> from) {
        return new TypeCell<>(index, from.dvRelation);
    }

    @IntRange(from = 0)
    private final int index;

    @IntRange(from = 1)
    private int maxSize;

    void updateMaxSize(@IntRange(from = 1) int maxSize) {
        this.maxSize = maxSize;
    }

    @NonNull
    private final DataVhMappingPool.DVRelation<T> dvRelation;

    private final List<String> subTypeTokens;

    boolean workFor(@NonNull String clz) {
        return clz.equals(dvRelation.getDataClz().getName());
    }

    TypeCell(@IntRange(from = 0) int index, @NonNull DataVhMappingPool.DVRelation<T> dvRelation) {
        this.index = index;
        this.dvRelation = dvRelation;
        subTypeTokens = new ArrayList<>();
    }

    int getSubTypeCount() {
        return dvRelation.one2N();
    }

    int getItemViewType(T data) {
//            dvRelation.getDataClz().equals(data.getClass()) 暂不做校验
        String token = dvRelation.subTypeToken(data);
        if (!subTypeTokens.contains(token)) {
            subTypeTokens.add(token);
        }
        int index = subTypeTokens.indexOf(token);
        return this.index * maxSize + index;
    }

    ViewHolderCreator getVhCreator(int subTypeIndex) {
        String subTypeToken = subTypeTokens.get(subTypeIndex);
        return dvRelation.getVhCreator(subTypeToken);
    }

    private String dvRelationToString() {
        return DataVhMappingPool.dvRelationToString(dvRelation,getSubTypeCount());
    }

    @Override
    public String toString() {
        return "TypeCell{" +
                "index=" + index +
                ", maxSize=" + maxSize +
                ",\r\n    dvRelation=" + dvRelationToString() +
                ",\r\n    subTypeTokens=" + subTypeTokens +
                "\r\n}";
    }
}
