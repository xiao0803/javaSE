package demo.lru;

import java.util.HashMap;

public class LRUCache {

	private Node head;

	private Node end;

	private int limit;// 表示存储上限

	private HashMap<String, Node> hashMap;

	public LRUCache(int limit) {
		this.limit = limit;
		hashMap = new HashMap<String, Node>();
	}

	public String get(String key) {
		Node node = hashMap.get(key); // 先根据Key获取节点
		if (node == null) { // 判断此节点是否存在
			return null;
		}
		refreshNode(node);// 在获取value之前先要移动节点位置
		// 因为采用哈希链表的数据结构 最左边（头节点）为不经常访问的节点 最右侧（尾节点）为最近访问的节点
		return node.getValue();
	}

	public synchronized void put(String key, String value) {
		Node node = hashMap.get(key);

		if (node == null) { // node为空说明key—value不存在 可以插入
			if (hashMap.size() >= limit) { // 节点数目到了指定的最大的缓存数量
				// 移除头节点
				String oldKey = removeNode(head);
				hashMap.remove(oldKey);
			}

			node = new Node(key, value);
			addNode(node);
			hashMap.put(key, node);
		} else { // key-value存在 刷新节点即可
			node.setValue(value);
			refreshNode(node);
		}
	}

	// 移除节点
	public synchronized void remove(String key) {
		Node node = hashMap.get(key);

		if (node == null) {
			return;
		}

		refreshNode(node);
		hashMap.remove(key);
	}

	// 更新节点
	private synchronized void refreshNode(Node node) {
		if (node == end) { // 尾节点不用移动 因为尾节点就是最新被访问节点的位置
			return;
		}
		// 移除节点
		removeNode(node);

		// 插入节点
		addNode(node);
	}

	// 在尾部插入节点
	private void addNode(Node node) {
		if (end != null) {
			end.next = node;
			node.pre = end;
			node.next = null;
		}

		end = node;

		if (head == null) {
			head = node;
		}
	}

	// 移除节点
	private String removeNode(Node node) {
		if (node == end) {
			end = end.pre;// 移除尾节点
		} else if (node == head) {
			head = head.next; // 移除头节点
		} else {
			node.pre.next = node.next;
			node.next.pre = node.pre;
		}

		return node.key;
	}
}
