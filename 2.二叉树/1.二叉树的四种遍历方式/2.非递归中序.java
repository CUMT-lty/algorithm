import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curNode = root;
        while (curNode!=null || stack.empty()==false){
            if (curNode!=null){   // 是否走到头
                stack.push(curNode);      //压栈
                curNode = curNode.left;   // 往左走
            } else {              // 如果已经走到头
                curNode = stack.pop();   // 出栈
                res.add(curNode.val);    // 访问，和先序的区别在于先序是在入栈前访问，中序是在出栈后访问
                curNode = curNode.right;   // 往右走
            }
        }
        return res;
    }
}