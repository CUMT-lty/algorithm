import java.util.ArrayList;
import java.util.List;
class Solution {

    public void preorder(TreeNode root, List<Integer> res) {
        if (root==null) return;
        res.add(root.val);             // 访问根结点
        preorder(root.left, res);      // 先序遍历左子树
        preorder(root.right, res);     // 先序遍历右子树
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        preorder(root, res);
        return res;
    }
}