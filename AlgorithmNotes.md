---
title: "算法刷题笔记"
tags: "题号对应的是leetcode主站题号"
---

# 写在开始之前
1.  养成边界检查的习惯，先判断边界条件，有时边界情况不止一种
2.  定下思考方向，搭好框架，再完善实现细节

# 链表
## 反转链表
1. 简单从头到尾反转链表:（题206）
```java
// 头指针head
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
```

2. 区间反转：哑节点处理边界情况（题92）
```java
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
```

3. 两个一组反转链表，难点：就地反转，组内反转而组间顺序不变（题24）
```java
class Solution {      // 循环
    public ListNode swapPairs(ListNode head) {
        if (head==null || head.next==null) return head; // 处理边界条件
        ListNode node1 = head;  // 总是记录待反转的两个节点
        ListNode node2 = head.next ;
        node1.next = node2.next;
        node2.next = node1;
        head = node2;
        while(node1.next!=null && node1.next.next!=null){    // 能找到一个新的组
            ListNode tmp = node1;
            // 移动
            node1 = node1.next;
            node2 = node1.next;
            // 翻转链接
            node1.next = node2.next;
            node2.next = node1;
            tmp.next = node2;
        }
        return head;
    }
}

class Solution {      // 递归写法
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode node1 = head;
        ListNode node2 = head.next;
        node1.next = swapPairs(node2.next);
        node2.next = node1;
        return node2;
    }
}

```
4. k个一组反转链表
- 递归思路：
	1. 边界条件：判断是否够一组，如果不够一组直接return
	2. 够一组的话处理当前组的反转
	3. 把后面的结果链接上，然后返回最终结果
```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        // 递归思路，先反转本组，然后把后面的递归结果连接上，如果不成一组就不反转了
        ListNode tmp = head;
        // 成组才反转否则直接返回head
        for(int i=0; i<k; i++){
            if(tmp==null) return head;
            tmp = tmp.next;
        }
        // 反转本组
        ListNode pre = head;
        ListNode back = null;
        for(int i=0; i<k; i++){
            tmp = pre.next;
            pre.next = back;
            back = pre;
            pre = tmp;
        }
        head.next = reverseKGroup(pre,k);
        return back;
    }
}
```
- 非递归解法：
  - 非递归解法要考虑得多一点因为要记录上一组的末尾结点，所以引入哑结点，这样比较好处理初始情况。因为要返回的是第一组反转后的头指针，而第一组够k个和不够k个返回头指针的情况是不一样的，所以把第一组不够k个也作为一种边界条件来单独处理。
  - 循环部分的步骤：
    1. 进入循环的条件：本组存在，也就是组头指针不为空
    2. 进入循环后，先判断本组够不够k个，不够k个不需要反转，并且说明本组是最后一组了，可以return了
    3. 如果本组够k个：反转本组，把本组链上，更新标记指针
    4. 进入下一轮循环
```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        // 边界情况，不反转
        if(k==0) return head;
        // 边界情况：第一组不够k个
        ListNode p = head;
        for(int i=0; i<k; i++){
            if(p==null) return head;
            p = p.next;
        }
        // 其他一般情况：第一组够k个
        ListNode dummyNode = new ListNode();   // 添加一个哑结点
        dummyNode.next = head;
        ListNode lastGroupTail = dummyNode;   // 上一组的尾巴
        ListNode groupHead = head;  // 记录组头
        for(int i=0; i<k-1; i++){
            head = head.next;
        }  // 把head移到正确的位置
        while(groupHead != null ){     // 如果还有一组
            ListNode tmp = groupHead;
            for(int i=0; i<k; i++){      // 看是否够一组
                // 不够k个的话说明本轮是最后一组了，此时groupHead不会变
                if(tmp==null) return dummyNode.next;
                tmp = tmp.next;
            }
            // 如果够一组了，反转本组
            ListNode pre = groupHead;
            ListNode back = null;
            for(int i=0; i<k; i++){
                ListNode temp = pre.next;
                pre.next = back;
                back = pre;
                pre = temp;
            }
            // 将本组链上
            groupHead.next = pre;
            lastGroupTail.next = back;
            // 更新
            lastGroupTail = groupHead;
            groupHead = pre;
        }
        return head;
    }
}
```
## 环形链表
## 链表合并
## 求链表中间结点

# 栈和队列

1.  辅助栈（题155：O(1)找min）

# 堆

# 字符串

# 递归

# 查找

1.  注意利用序列的有序性
2.  矩阵转化为二叉树：在一个 n \* m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
- ![](images/2022-03-13-00-02-42.png)
- 旋转，视为一棵二叉排序树

# 哈希

# 贪心

# 动态规划

# 遇到的一些小问题

1.  java的条件判断是短路式判断，下面两种写法的区别在于：条件判断会从左往右判断，如果先判断：nums[right]>target，这一句，有可能发生数组下标越界，但是把right>=0放在左边先判断的话，如果不满足这个要求，在短路式判断下整个与表达式就会被判错，后半句就不会参与判断，就不会发生数组下标越界了。

```java
while(right>=0 && nums[right]>target) right--;
while(nums[right]>target && right>=0) right--;
```

