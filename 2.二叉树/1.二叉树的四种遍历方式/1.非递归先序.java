import java.util.Stack;
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();;
        List<Integer> res = new ArrayList<Integer>();
        TreeNode curNode = root;    // 当前正在访问的结点
        while(curNode!=null || stack.isEmpty() == false){  // 栈非空说明还没访问
            if (curNode!=null) {      // 左子树非空就一直往左走
                res.add(curNode.val);      // 访问
                stack.push(curNode);       // 入栈
                curNode = curNode.left;    // 往左走
            } else {
                curNode = stack.pop();  // 出栈
                curNode = curNode.right;  // 往右走
            }
        }
        return res;
    }
}