import java.util.ArrayList;
import java.util.List;
class Solution {
    public void postorder(TreeNode root, List<Integer> res){
        if (root==null) return;
        postorder(root.left, res);        // 后序遍历左子树
        postorder(root.right, res);       // 后序遍历右子树
        res.add(root.val);                // 访问根结点
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        postorder(root, res);
        return res;
    }
}