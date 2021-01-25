package com.kxingyi.common.web.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author byliu
 **/
public class TreeNode {

    private List<TreeNode> children = new ArrayList<>();
    private String icon;
    private boolean isLeaf;
    private boolean selectable = true;
    private boolean selected = false;
    private String title;
    private String key;
    // 节点的父级ID
    private String pid;
    private boolean expanded;
    private boolean checked = false;
    private boolean disabled = false;
    private int orderNumber;

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public TreeNode(String icon, boolean isLeaf, boolean selectable, boolean selected, String title, String key, String pid, boolean expanded, boolean checked, boolean disabled, int orderNumber) {
        this.icon = icon;
        this.isLeaf = isLeaf;
        this.selectable = selectable;
        this.selected = selected;
        this.title = title;
        this.key = key;
        this.pid = pid;
        this.expanded = expanded;
        this.checked = checked;
        this.disabled = disabled;
        this.orderNumber = orderNumber;
    }

    public TreeNode(String icon, boolean isLeaf, String title, String key, boolean expanded, String pid, int orderNumber) {
        this.icon = icon;
        this.isLeaf = isLeaf;
        this.title = title;
        this.key = key;
        this.expanded = expanded;
        this.pid = pid;
        this.orderNumber = orderNumber;
    }

    public TreeNode() {
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "children=" + children +
                ", icon='" + icon + '\'' +
                ", isLeaf=" + isLeaf +
                ", selectable=" + selectable +
                ", selected=" + selected +
                ", title='" + title + '\'' +
                ", key='" + key + '\'' +
                ", expanded=" + expanded +
                ", checked=" + checked +
                ", disabled=" + disabled +
                '}';
    }
}
