class Solution {  // 递归
	public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }
    ListNode revHead = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return revHead;
    }
}