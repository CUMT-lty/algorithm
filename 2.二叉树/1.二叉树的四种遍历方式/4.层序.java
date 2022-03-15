import java.util.*;
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root==null) return res;                              // 如果树根为空，直接返回空列表
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);     // 先把根结点入队
        while(queue.size()!=0){     // 外层循环一次处理一层
            int count = queue.size();        // 本层结点个数
            List<Integer> tmpRes = new ArrayList<Integer>();  // 存放本层所有结点的数组
            while(count!=0){      // 处理本层
                TreeNode node = queue.poll();         // 出队
                tmpRes.add(node.val);                 // 输出
                if (node.left!=null) queue.offer(node.left);          // 如果有左孩子，左孩子入队
                if (node.right!=null) queue.offer(node.right);        // 如果有右孩子，右孩子入队
                count--;
            }
            res.add(tmpRes);        // 将本层结果加入最终结果中
        }
        return res;
    }
}