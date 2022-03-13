class Solution {  // 非递归
	public ListNode reverseList(ListNode head) {
		if(head==null || head.next==null) return head;   // 处理边界条件
		ListNode pre;
		ListNode back;
		ListNode tmp;
		back = head;
		pre = back.next;
    while(pre!=null){
			tmp = pre.next;
			pre.next = back;
			back = pre;
			pre = tmp;
		}
		head.next=null;
		return back;
    }
}