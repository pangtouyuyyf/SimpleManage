package com.simple.manage.system.entity;

import lombok.Data;

import java.util.List;

/**
 * Description 树
 * Author chen
 * CreateTime 2018-12-18 11:44
 **/
public class Tree {
    private int key;

    private String title;

    private boolean isLeaf;

    private List<Tree> children;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }
}
