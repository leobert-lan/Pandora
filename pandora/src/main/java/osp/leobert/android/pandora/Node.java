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
}
