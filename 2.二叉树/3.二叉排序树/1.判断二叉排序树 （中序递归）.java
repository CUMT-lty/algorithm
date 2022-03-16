class Solution {
    double backVal = -Double.MAX_VALUE;
    public boolean _isValidBST(TreeNode root){
        if (root==null) return true;
        boolean l = _isValidBST(root.left);
        if (root.val<=backVal) return false;   // 在访问的位置加判断语句
        backVal = root.val;
        boolean r = _isValidBST(root.right);
        return l && r;
    }
    public boolean isValidBST(TreeNode root) {
        return _isValidBST(root);
    }
}