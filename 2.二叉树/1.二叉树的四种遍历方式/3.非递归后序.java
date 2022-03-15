import java.util.*;
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        HashSet<TreeNode> visited = new HashSet<TreeNode>();    // 记录从走过右路径的结点
        TreeNode curNode = root;
        while(curNode!=null || stack.empty()==false){
            while (curNode!=null){       // 一直往左走，走到头
                stack.push(curNode);
                curNode = curNode.left;
            }
            TreeNode tmp = stack.peek();
            if (tmp.right!=null && visited.contains(tmp.right)==false){     // 如果有右子树且右子树没有被访问过
                curNode = tmp.right;       // 向右走
                visited.add(curNode);      // 标记以此为根的右子树被访问过了
            } else {
                res.add(tmp.val);
                stack.pop();
            }
        }
        return res;
    }
}