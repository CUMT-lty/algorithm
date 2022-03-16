class Solution {
    public int maxDepth(TreeNode root) {
        if (root==null) return 0;      // 走到底就返回0
        return 1+Math.max(maxDepth(root.left), maxDepth(root.right));   // 否则返回最大深度+1
    }
}