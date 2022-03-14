class Solution {
    public ListNode mergeTwo(ListNode list1, ListNode list2){ // 合并两个链表
        if(list1==null) return list2;
        if(list2==null) return list1;
        if(list1.val<list2.val){
            list1.next = mergeTwo(list1.next,list2);
            return list1;
        } else {
            list2.next = mergeTwo(list1,list2.next);
            return list2;
        }
    }
    public ListNode _mergeKLists(ListNode[] lists, int start, int end) { // 分别是头尾索引
        if(lists.length==0) return null;  // 没有链表
        if(start==end) return lists[start];  // 只剩一个
        if(start+1==end) return mergeTwo(lists[start], lists[end]);  // 两个的时候返回
        // 分治
        int mid = (int)Math.ceil((double)(start+end)/2);
        ListNode left = _mergeKLists(lists, start, mid);
        ListNode right = _mergeKLists(lists, mid+1, end);
        return mergeTwo(left, right);
    }
    public ListNode mergeKLists(ListNode[] lists) {
        return _mergeKLists(lists, 0, lists.length-1);
    }
}