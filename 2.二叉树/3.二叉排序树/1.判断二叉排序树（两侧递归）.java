class Solution {
    public boolean isValidBST(TreeNode root,long low, long upper) {  // 增加两个参数
        if (root==null) return true;
        if (root.val<=low || root.val>=upper) return false;          // 根结点是否在指定的开区间内
        boolean flagLeft = isValidBST(root.left, low, root.val);     // 更新区间往下判断
        boolean flagRight = isValidBST(root.right, root.val, upper);
        return flagLeft && flagRight;
    }
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
}