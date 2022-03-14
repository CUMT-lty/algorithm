class Solution {

	public void reverseList(ListNode head) {  // 反转链表方法
		if(head==null || head.next==null) return;  // 处理边界条件
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
    }

	public ListNode reverseBetween(ListNode head, int left, int right) {
		if(head==null || head.next==null || left==right) return head;
		ListNode dummyNode = new ListNode(-1);  // 添加一个哑节点（相当于空的头结点）
		dummyNode.next = head;
		// 找需要记录的位置
		ListNode startBefore = dummyNode; // 添加了哑节点之后才能这样赋初值
		ListNode start = head;
		ListNode end = start;
		ListNode endNext = end.next;
        int i = 1;
		while(i<right){   // 找四个应该保存的节点
			if(i<left){ // 找start
				start = start.next;
				startBefore = startBefore.next;
			}
			end = end.next;    // 找end
			endNext = endNext.next;
			i++;
        }
		end.next = null; //断开

		// 反转中段链
        reverseList(start);
		// 前后都链接上
		startBefore.next = end;
		start.next = endNext;
		// 分情况返回
		return startBefore == dummyNode ? end : head;
	}
}