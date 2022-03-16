class Solution {
    public TreeNode _sortedArrayToBST(int[] nums, int start, int end) {
        if (start>end) return null;     // 空结点
        if (start==end) return new TreeNode(nums[start]);     // 只剩一个结点

        int mid = (start+end)/2;       // 找中点
        TreeNode root = new TreeNode(nums[mid]);
        root.left = _sortedArrayToBST(nums, start, mid-1);      // 把左右递归结果挂上
        root.right = _sortedArrayToBST(nums, mid+1, end);
        return root;
    }
    public TreeNode sortedArrayToBST(int[] nums) {
        TreeNode res = _sortedArrayToBST(nums, 0, nums.length-1);
        return res;
    }
}