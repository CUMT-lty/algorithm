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
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length==0) return null;  // 没有链表
        if(lists.length==1) return lists[0];  // 只有一个链表
        // 2路并归合并
        for(int size=1; size<=lists.length; size*=2){  // 归并趟数，size是当次步长
            for(int i=0; i<lists.length; i+=2*size){  // 循环处理每一组
                if(i+size<lists.length){
                    lists[i] = mergeTwo(lists[i],lists[i+size]);
                }
            }
        }
        return lists[0];
    }
}