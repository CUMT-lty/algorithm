---
title: "算法刷题笔记"
tags: "题号对应的是leetcode主站题号"
---

# 写在开始之前
1.  养成边界检查的习惯，先判断边界条件，有时边界情况不止一种
2.  定下思考方向，搭好框架，再完善实现细节

# 链表
## 反转链表
### 1. 简单从头到尾反转链表:（题206）
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
```
```java
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

### 2. 区间反转：哑节点处理边界情况（题92）
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
            if(i<left){   // 找start
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
        startBefore.next = end;
        start.next = endNext;
        return dummyNode.next;
    }
}
```

### 3. 两个一组反转链表，难点：就地反转，组内反转而组间顺序不变（题24）
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
```
```java
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
### 4. k个一组反转链表（题25）
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
### 1. 检测链表形成环（题141）
- 思路1：将结点保存在不允许保存重复元素的集合中，利用内存地址对结点进行判重
- 思路2：快慢指针，慢指针一次走一步，快指针一次走两步，如果两指针相遇，就说明形成环
  - 快慢指针相遇就说明形成环？
  - 如果有环，两指针最终都会走到环里，并且快指针走得快，类似相遇问题，所以两指针最终会相遇
  - 如果没有环，慢指针永远不会遇到快指针
```java
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
```
```java
import java.util.HashSet;        // 思路2：导入不可装入重复元素的集合
public class Solution {
    public boolean hasCycle(ListNode head) {
       HashSet<ListNode> nodeArr = new HashSet<ListNode>();    // 创建一个空集合
       while(head!=null){
           if(nodeArr.contains(head)) return true;
           nodeArr.add(head);
           head = head.next;
       }
       return false;
    }
}
```
### 2. 如何找到环的起点（题142）
在设置快慢指针的基础上，看作数学问题：
- ![](images/2022-03-14-15-34-17.png)
- 从而得到解决步骤：
  - 设置快慢指针，让快慢指针相遇
  - 设置新指针指向起点
  - 让慢指针和新指针同时前移，慢指针和新指针相遇的位置就是环的起点
```java
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
```

## 链表合并
### 1. 合并两个有序链表（题21）
- 递归解法：返回条件，有一个链表已经遍历完了，否则挑出一个最小的head，递归剩下的
```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1==null) return list2;
        if(list2==null) return list1;
        if(list1.val<list2.val) {
            list1.next = mergeTwoLists(list1.next,list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1,list2.next);
            return list2;
        }
    }
}
```
- 非递归解法：经典merge，就是归并排序里的合并算法
```java
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
```

### 2. 合并k个有序链表（题23）
- 思路：二路归并的循环实现和递归实现
- 思路一：循环二路归并
```java
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
```
- 思路2：递归二路归并
```java
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
```

## 求链表中间结点（题234）
### 1. 判断回文链表
- 思路：反转链表后半段，再和前半段一一比较
- 关键：找中点，用快慢指针。（这里边界的验证很好想，自己举一个奇数个结点的例子和一个偶数个结点的例子即可）
```java
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
```

# 栈和队列
## 栈和递归
### 1. 有效括号
- 区分左右括号
- 左括号直接入栈
- 如果是右括号入栈要判断此时是否栈空，栈空直接返回false，栈非空再判断栈顶是否是与之对应的左括号
```java
import java.util.Stack;
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (int i=0; i<s.length(); i++){
            char x = s.charAt(i);
            if (x=='(' || x=='[' || x=='{') stack.push(x);
            if (stack.empty()) return false;
            if (x==')' && stack.pop()!='(') return false;
            if (x==']' && stack.pop()!='[') return false;
            if (x=='}' && stack.pop()!='{') return false;
        }
        return stack.empty();
    }
}
```
### 2. 多维数组flatten（题341）
- 递归思路：只处理最外层，内层交给递归处理，例如：[1, [2, [3, [4, 5]]], 6]，按第一层划分为
- 1
- [2, [3, [4, 5]]]
- 6
- 如果是一个整数就直接加入结果集中，如果仍然是数组就将递归结果加入结果集中

### 3. O(1)找min（题155）
- 这道题很简单了，记录一下是因为很多和栈有关的题目都用到了辅助栈
- 这题是设置一个辅助栈，只有比主栈栈顶元素小的才入辅助栈，则栈的最小元素永远是辅助栈的栈顶

## 二叉树的层序遍历

## 无权图BFS遍历
## 实现优先队列
## 优先队列应用
## 双端队列及应用
## 栈和队列的相互实现


# 二叉树
## 二叉树的遍历
### 1. 二叉树的先序遍历
- 递归，思路：访问根节点，先序遍历左子树，先序遍历右子树
```java
import java.util.ArrayList;
import java.util.List;
class Solution {

    public void preorder(TreeNode root, List<Integer> res) {
        if (root==null) return;
        res.add(root.val);             // 访问根结点
        preorder(root.left, res);      // 先序遍历左子树
        preorder(root.right, res);     // 先序遍历右子树
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        preorder(root, res);
        return res;
    }
}
```
- 非递归，思路
  - 一直向左走，每走到一个结点就访问、压栈、向左，直到左边没有分支
  - 出栈，往右走
```java
import java.util.Stack;
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();;
        List<Integer> res = new ArrayList<Integer>();
        TreeNode curNode = root;    // 当前正在访问的结点
        while(curNode!=null || stack.isEmpty() == false){  // 栈非空说明还没访问
            if (curNode!=null) {      // 左子树非空就一直往左走
                res.add(curNode.val);      // 访问
                stack.push(curNode);       // 入栈
                curNode = curNode.left;    // 往左走
            } else {
                curNode = stack.pop();  // 出栈
                curNode = curNode.right;  // 往右走
            }
        }
        return res;
    }
}
```
### 2. 二叉树的中序遍历
- 递归和非递归的思路都类似二叉树的先序遍历
- 递归中序：
```java
import java.util.ArrayList;
import java.util.List;
class Solution {

    public void inorder(TreeNode root, List<Integer> res){
        if (root==null) return;
        inorder(root.left, res);          // 中序遍历左子树
        res.add(root.val);                // 访问根结点
        inorder(root.right, res);         // 中序遍历右子树
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        inorder(root, res);
        return res;
    }
}

```
- 非递归中序：
```java
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curNode = root;
        while (curNode!=null || stack.empty()==false){
            if (curNode!=null){   // 是否走到头
                stack.push(curNode);      //压栈
                curNode = curNode.left;   // 往左走
            } else {              // 如果已经走到头
                curNode = stack.pop();   // 出栈
                res.add(curNode.val);    // 访问，和先序的区别在于先序是在入栈前访问，中序是在出栈后访问
                curNode = curNode.right;   // 往右走
            }
        }
        return res;
    }
}
```
### 3. 二叉树的后序遍历
- 递归思路和先序中序类似
```java
import java.util.ArrayList;
import java.util.List;
class Solution {
    public void postorder(TreeNode root, List<Integer> res){
        if (root==null) return;
        postorder(root.left, res);        // 后序遍历左子树
        postorder(root.right, res);       // 后序遍历右子树
        res.add(root.val);                // 访问根结点
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        postorder(root, res);
        return res;
    }
}
```
- **非递归思路：后序的非递归做法比先序和中序要麻烦一点，因为出栈的时候有可能是从左子树返回的也有可能是从右子树返回的，所以要添加标记来区分，大致思路：
  - 先一路往左走走到头
  - 返回看栈顶结点是否有右子树且该右子树没有被访问过
  - 如果满足上述条件，则往右走，并标记此结点说明以此结点为根节点的右子树被访问过了
  - 如果不满足此条件，说明可以输出结点值了
- 这里有一个坑点，当要输出时，不能简单地让当次循环的curNode更新，因为如果更新了curNode并进入了新循环，会重复将左子树压栈。后面会记录这个错误写法。
```java
import java.util.*;
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        HashSet<TreeNode> visited = new HashSet<TreeNode>();    // 记录从走过右路径的结点
        TreeNode curNode = root;
        while(curNode!=null || stack.empty()==false){
            while (curNode!=null){       // 一直往左走，走到头
                stack.push(curNode);
                curNode = curNode.left;
            }
            TreeNode tmp = stack.peek();
            if (tmp.right!=null && visited.contains(tmp.right)==false){     // 如果有右子树且右子树没有被访问过
                curNode = tmp.right;       // 向右走
                visited.add(curNode);      // 标记以此为根的右子树被访问过了
            } else {
                res.add(tmp.val);
                stack.pop();
            }
        }
        return res;
    }
}
```
- 下面是一个错误写法
```java
if(curNode.right!=null && visited.contains(curNode.right)==false){   // 如果右子树存在，而且右子树未被访问
    curNode = curNode.right;
    visited.add(curNode);    // 以此为根的右子树被访问过了
} else {
    res.add(curNode.val);
    stack.pop();
    curNode = stack.pop();     // 这里不能更新curNode，如果更新了并带入了新一轮的循环，就会导致左子树结点重复压栈
}
```

### 4. 二叉树的层序遍历（题102）
- 二叉树的层序遍历借助队列实现
- **这题不止要遍历，还要体现层次。当本层最后一个结点出队后，队中剩下的结点个数就是下一层的节点数
```java
import java.util.*;
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root==null) return res;                              // 如果树根为空，直接返回空列表
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);     // 先把根结点入队
        while(queue.size()!=0){     // 外层循环一次处理一层
            int count = queue.size();        // 本层结点个数
            List<Integer> tmpRes = new ArrayList<Integer>();  // 存放本层所有结点的数组
            while(count!=0){      // 处理本层
                TreeNode node = queue.poll();         // 出队
                tmpRes.add(node.val);                 // 输出
                if (node.left!=null) queue.offer(node.left);          // 如果有左孩子，左孩子入队
                if (node.right!=null) queue.offer(node.right);        // 如果有右孩子，右孩子入队
                count--;
            }
            res.add(tmpRes);        // 将本层结果加入最终结果中
        }
        return res;
    }
}
```

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
