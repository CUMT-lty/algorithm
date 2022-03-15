import java.util.ArrayList;
import java.util.List;
class Solution {

    public void inorder(TreeNode root, List<Integer> res){
        if (root==null) return;
        inorder(root.left, res);          // 中序遍历左子树
        res.add(root.val);                // 访问根结点
        inorder(root.right, res);         // 中序遍历右子树
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        inorder(root, res);
        return res;
    }
}