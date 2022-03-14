class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1==null) return list2;
        if(list2==null) return list1;
        ListNode dummyNode = new ListNode();         // 添加哑结点
        ListNode p = dummyNode;
        while(list1!=null && list2!=null){
            if(list1.val<list2.val){
                p.next = list1;
                p = p.next;
                list1 = list1.next;
            } else {
                p.next = list2;
                p = p.next;
                list2 = list2.next;
            }
        }
        if(list1==null) p.next = list2;       // 后处理
        if(list2==null) p.next = list1;
        return dummyNode.next;
    }
}