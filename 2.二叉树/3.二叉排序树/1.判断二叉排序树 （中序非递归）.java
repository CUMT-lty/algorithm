class Solution {
    public boolean isValidBST(TreeNode root) {    // 中序循环遍历模板
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curNode = root;
        double backVal = -Double.MAX_VALUE;
        while (curNode!=null || stack.empty()==false){
            while (curNode!=null){   // 是否走到头
                stack.push(curNode);      //压栈
                curNode = curNode.left;   // 往左走
            }
            // 如果已经走到头
            curNode = stack.pop();   // 出栈
            if(curNode.val<=backVal) return false;
            backVal = curNode.val;;    // 始终保存中序前值
            curNode = curNode.right;   // 往右走
        }
        return true;
    }
}