package com.simple.manage.system.entity;

import lombok.Data;

import java.util.List;

/**
 * Description 树
 * Author chen
 * CreateTime 2018-12-18 11:44
 **/
@Data
public class Tree {
    private int key;

    private String title;

    private boolean isLeaf;

    private List<Tree> children;
}
