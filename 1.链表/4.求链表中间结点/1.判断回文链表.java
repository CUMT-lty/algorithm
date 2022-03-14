class Solution {

    public ListNode reverseList(ListNode head){  // 反转链表
        if(head==null || head.next==null) return head;
        ListNode back = null;
        ListNode pre = head;
        while(pre!=null){
            ListNode tmp = pre.next;
            pre.next = back;
            back = pre;
            pre = tmp;
        }
        return back;
    }

    public boolean isValEqual(ListNode list1, ListNode list2){ // 以更短的链表为准，判断结点值是否逐个相等
        while(list2!=null){  // 以后半段的长度为准
            if(list1.val!=list2.val) return false;
            list1 = list1.next;
            list2 = list2.next;
        }
        return true;
    }

    public boolean isPalindrome(ListNode head) {
        if(head.next==null) return true;
        ListNode slow = head; // 找中点，快慢指针
        ListNode fast = head.next;
        while(fast!=null && fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;   
        }
        // 奇数时slow会停在中点一个位置，此时fast==null
        // 偶数时slow会停前半段的最后一个位置，此时fast!=null
        // 可以通过fast的状态来判断此时slow是什么状态
        // 但是没有必要因为此时后半段head一定是slow.next
        ListNode headTmp = reverseList(slow.next);
        return isValEqual(head, headTmp);   // 这里后半段的头部是headTmp，以这个长度为准
    }
}