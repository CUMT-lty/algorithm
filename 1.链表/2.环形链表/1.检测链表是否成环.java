public class Solution {          // 思路1：快慢指针
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast!=null && fast.next!=null){      // 如果尾部有空指针，说明无环
            slow = slow.next;     // 初始状态是无环的
            fast = fast.next.next;
            if(fast==slow) return true;
        }
        return false;
    }
}