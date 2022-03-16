class Solution {
    public int minDepth(TreeNode root) {
        if (root==null) return 0;
        int level = 0;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();    // 队列
        queue.offer(root);
        while (queue.size()!=0){
            int count = queue.size();
            if (count!=0) level++;  // 队列不为空则层数+1
            while (count!=0){       // 处理本层
                TreeNode tmp = queue.poll();
                if (tmp.left==null && tmp.right==null) return level;   // 发现本层有叶节点就直接返回当前层数
                else{
                    if (tmp.left!=null) queue.offer(tmp.left);
                    if (tmp.right!=null) queue.offer(tmp.right);
                }
                count--;
            }
        }
        return level;
    }
}