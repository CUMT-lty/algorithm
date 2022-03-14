public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while(fast!=null && fast.next!=null){  // 是否有环，如果跳出此循环说明无环，返回null
            fast = fast.next.next;
            slow = slow.next;
            if(fast==slow){     // 快慢指针相遇说明有环
                ListNode tmp = head;     // 设置新指针
                while(tmp!=slow){        // 新指针和慢指针同时前移，相遇处就是环的起点
                    tmp = tmp.next;
                    slow = slow.next;
                }
                return tmp;
            }
        }
        return null;
    }
}