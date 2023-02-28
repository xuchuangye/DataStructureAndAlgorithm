package com.mashibing.coding;

import com.mashibing.node.HuffmanNode;

import java.io.*;
import java.util.*;

/**
 * 赫夫曼编码
 * 赫夫曼编码是一种编码方式, 属于一种程序算法
 * <p>
 * 赫夫曼编码是无损压缩
 *
 * @author xcy
 * @date 2022/3/27 - 11:39
 */
public class HuffmanCoding {
	public static final Map<Byte, String> huffmanCodes = new HashMap<>();

	public static final StringBuilder stringBuilder = new StringBuilder();

	public static void main(String[] args) throws Exception {
		/*String content = "i like like like java do you like a java";
		byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);*/

		/*List<HuffmanNode> nodes = getNodes(contentBytes);
		 *//*for (HuffmanNode node : nodes) {
			System.out.println(node);
		}*//*
		HuffmanNode root = createHuffmanTree(nodes);
		//preOrder(root);
		//获取赫夫曼树编码
		Map<Byte, String> huffmanCodes = getHuffmanCodes(root);
		System.out.println("赫夫曼树编码");
		System.out.println(huffmanCodes);

		//获取压缩之后的赫夫曼树对应的数组
		byte[] huffmanCodesBytes = getHuffmanCodesBytesZip(contentBytes, huffmanCodes);
		System.out.println("压缩之后的赫夫曼树对应的数组");
		System.out.println(Arrays.toString(huffmanCodesBytes));*/
		/*byte[] huffmanCodesBytes = huffmanCodesZipBytes(contentBytes);
		System.out.println("压缩之后的赫夫曼树对应的数组");
		System.out.println(Arrays.toString(huffmanCodesBytes));

		byte[] decode = decode(huffmanCodes, huffmanCodesBytes);
		System.out.println(new String(decode));*/

		//测试压缩文件
		/*String zipFile = "I:\\image.jpg";
		String dstFile1 = "I:\\image.zip";
		huffmanCodesZipFile(zipFile, dstFile1);
		System.out.println("压缩文件成功！");*/

		//测试解压文件
		String unZipFile = "I:\\image.zip";
		String dstFile2 = "I:\\image2.jpg";
		huffmanCodesUnZipFile(unZipFile, dstFile2);
		System.out.println("解压文件成功！");

		/*FileInputStream is = new FileInputStream("I:\\image.png");
		byte[] bytes1 = new byte[is.available()];
		is.read(bytes1);
		byte[] huffmanCodesZipBytes = huffmanCodesZipBytes(bytes1);
		System.out.println(Arrays.toString(huffmanCodesZipBytes));
		FileOutputStream os = new FileOutputStream("I:\\image.zip");
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(huffmanCodesZipBytes);
		oos.writeObject(huffmanCodes);
		oos.close();
		os.close();
		is.close();*/

		/*FileInputStream is = new FileInputStream("I:\\image.zip");
		ObjectInputStream ois = new ObjectInputStream(is);
		byte[] bytes = (byte[]) ois.readObject();
		Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();
		byte[] decode = decode(huffmanCodes, bytes);
		FileOutputStream os = new FileOutputStream("I:\\image2.png");
		os.write(decode);

		os.close();
		ois.close();
		is.close();*/
	}

	/**
	 * 前序遍历
	 *
	 * @param root 根节点
	 */
	public static void preOrder(HuffmanNode root) {
		if (root != null) {
			root.preOrder();
		} else {
			System.out.println("赫夫曼树为空，无法进行遍历");
		}
	}

	/**
	 * 解压文件到指定目录中
	 *
	 * @param unZipFile 解压的目标文件
	 * @param dstFile   解压之后指定的文件位置
	 */
	public static void huffmanCodesUnZipFile(String unZipFile, String dstFile) {
		FileInputStream is = null;
		ObjectInputStream ois = null;
		FileOutputStream os = null;
		try {
			//创建文件输入流
			is = new FileInputStream(unZipFile);
			//创建对象输入流
			ois = new ObjectInputStream(is);
			//获取赫夫曼数组
			byte[] huffmanBytes = (byte[]) ois.readObject();
			//获取赫夫曼编码
			Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();
			//解码
			byte[] bytes = decode(huffmanCodes, huffmanBytes);
			//创建文件输出流
			os = new FileOutputStream(dstFile);
			//将解码后的数组写入到文件中
			os.write(bytes);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (ois != null)
					ois.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 压缩文件到指定目录
	 *
	 * @param zipFile 需要使用赫夫曼编码进行压缩的文件名（文件的全路径）
	 * @param dstFile 需要写入压缩之后的文件位置
	 */
	public static void huffmanCodesZipFile(String zipFile, String dstFile) {
		FileInputStream is = null;
		FileOutputStream os = null;
		ObjectOutputStream oos = null;
		try {
			//1、创建输入流
			is = new FileInputStream(zipFile);
			//2、创建和文件大小一样的字节数组
			byte[] bytes = new byte[is.available()];
			//2.1、文件流读取字节数组
			is.read(bytes);
			//2.2、直接使用赫夫曼编码对数组进行压缩
			byte[] huffmanCodesZipBytes = huffmanCodesZipBytes(bytes);

			//3、创建输出流
			os = new FileOutputStream(dstFile);
			//4、创建对象输出流
			oos = new ObjectOutputStream(os);
			//4.1、以对象流的方式将压缩之后的字节数组进行输出，写入到压缩文件中
			oos.writeObject(huffmanCodesZipBytes);
			//4.2、以对象流的方式将赫夫曼编码进行输出，写入到压缩文件中，方便恢复源文件时使用
			oos.writeObject(huffmanCodes);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (oos != null) {
					oos.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * 对使用赫夫曼编码压缩之后的数组进行解码，解码为原始字符串的byte数组
	 *
	 * @param huffmanCodes 赫夫曼编码
	 * @param huffmanBytes 赫夫曼编码对应的字节数组
	 * @return 返回原始字符串的byte数组
	 */
	public static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
		//1、将赫夫曼对应的数组转换成二进制字符串，并且使用StringBuilder进行拼接
		StringBuilder stringBuilder = new StringBuilder();
		//boolean flag = false;
		byte last;
		//遍历从数组的第0个元素到倒数第二个元素结束
		for (int i = 0; i < huffmanBytes.length - 1; i++) {
			byte b = huffmanBytes[i];
			//判断是否需要补码，如果是数组的最后一个字节元素，那么就不需要补码，如果是最后一个字节元素，那么就需要补码
			stringBuilder.append(byteToBitString(true, b));
		}

		//单独处理huffmanBytes数组的最后一个元素
		last = huffmanBytes[huffmanBytes.length - 1];
		String lastByteString = byteToBitString(false, last);

		//判断长度是否相等，如果正好相等，那么直接进行拼接
		if (stringBuilder.length() + lastByteString.length() == huffmanBytes.length) {
			stringBuilder.append(lastByteString);
		} else {
			//如果正好不相等，那么判断长度是否小于huffmanBytes数组的总长度
			while (stringBuilder.length() + lastByteString.length() < huffmanBytes.length) {
				//如果长度不够，那就先补0
				stringBuilder.append(0);
			}
			//直到总长度相等，再拼接
			stringBuilder.append(lastByteString);
		}

		System.out.println("解码后的哈夫曼编码字符串为：" + stringBuilder.toString() + "长度为：" + stringBuilder.length());
		//System.out.println(stringBuilder);

		//2、将二进制字符串按照指定的赫夫曼编码进行解码
		//将赫夫曼编码 表进行调换，a -> 100 调换为 100 -> a
		Map<String, Byte> map = new HashMap<String, Byte>();
		for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}
		//System.out.println(map);

		//3、创建集合，存放byte
		List<Byte> list = new ArrayList<>();
		//扫描StringBuilder，依次遍历
		//i < stringBuilder.length() - 1表示如果最后一位是0，那么就直接忽略
		for (int i = 0; i < stringBuilder.length() - 1; ) {
			//i只是前进所用，真正扫描寻找到有为止，还需要一个索引count
			int count = 1;
			//遍历查找的结束标识
			boolean isMatching = true;
			String key = null;
			//byte b;
			while (isMatching && (i + count) <= stringBuilder.length()) {
				//101010001011111111...
				//递增的取出key
				key = stringBuilder.substring(i, count + i);//i不需要移动，count移动i位，指定进行匹配字符
				//b = map.get(key);
				if (map.containsKey(key)) {
					//说明匹配到，退出循环
					isMatching = false;
				} else {
					//说明没有匹配到，继续count++
					count++;
				}
			}
			//如果匹配到就将byte添加到List集合中
			list.add(map.get(key));
			//i移动到count
			i += count;
		}
		//创建原始字符串的byte数组
		byte[] bytes = new byte[list.size()];
		//依次从List中取出字节元素添加到bytes数组中
		for (int i = 0; i < list.size(); i++) {
			bytes[i] = list.get(i);
		}
		return bytes;
	}

	/**
	 * 将一个byte转成一个二进制的字符串并返回
	 *
	 * @param isEnoughSub 是否足够补高位，true表示需要补码，false表示不需要补码，如果是最后一位，就不需要补高位
	 * @param b           byte类型的整数
	 * @return 需要按照补码进行返回
	 */
	public static String byteToBitString(boolean isEnoughSub, byte b) {
		int temp = b;
		// 转化
		// 正数转化呢，只有后几位，--- 所以需要补位0 补齐8 位
		// 256 1 0000 0000 与256或计算，就可以得到后八位长度 负数不影响，只要后8位
		if (isEnoughSub) {
			temp |= 256; //256 -> 1 0000 0000 | 0000 0001 => 1 0000 0001
		}
		String binaryString = Integer.toBinaryString(temp);
		if (isEnoughSub || temp < 0) {
			// true 就是还没到末尾 都是8位 以及负数的情况
			// 但是注意！负数转化完是int类型32位的二进制补码 --> 所以需要取出后8位
			return binaryString.substring(binaryString.length() - 8);
		} else {
			// false 到了末尾值，正数，不足8位，直接返回该有的长度就可以
			return binaryString;
		}
	}

	/**
	 * 将一个数组转使用赫夫曼编码换成压缩之后的字节数组
	 *
	 * @param bytes 原始字符串对应的byte[]数组
	 * @return 压缩之后的字节数组
	 */
	public static byte[] huffmanCodesZipBytes(byte[] bytes) {
		//接收一个字节数组，返回Node节点组成的List集合
		List<HuffmanNode> huffmanNodes = getNodes(bytes);
		//创建赫夫曼树，并且获取赫夫曼树的根节点
		HuffmanNode root = createHuffmanTree(huffmanNodes);
		//获取赫夫曼树编码
		Map<Byte, String> huffmanCodes = getHuffmanCodes(root);
		//获取压缩之后的赫夫曼树对应的数组
		return huffmanCodesZipBytes(bytes, huffmanCodes);
	}

	/**
	 * @param bytes        压缩之前的原始字符串对应的byte[]数组
	 * @param huffmanCodes 赫夫曼编码
	 * @return 返回根据赫夫曼编码压缩之后的字节数组
	 */
	public static byte[] huffmanCodesZipBytes(byte[] bytes, Map<Byte, String> huffmanCodes) {
		//用于拼接原始字符串对应的byte[]数组中的每一个元素
		StringBuilder stringBuilder = new StringBuilder();
		for (byte b : bytes) {
			stringBuilder.append(huffmanCodes.get(b));
		}
		//System.out.println("赫夫曼树的编码：" +stringBuilder);

		//转换压缩之后的赫夫曼树对应的数组的长度
		int len = 0;
		//stringBuilder.length() / 8 == 0 表示刚好能够存放n个8位数
		if (stringBuilder.length() / 8 == 0) {
			len = stringBuilder.length() / 8;
		} else {
			//否则就表示存放不下n个8位数，需要扩容，那么就加1即可
			len = stringBuilder.length() / 8 + 1;
		}
		//压缩之后的赫夫曼树对应的数组的长度
		byte[] huffmanCodesBytes = new byte[len];

		//压缩之后的赫夫曼树对应的数组的索引
		int index = 0;

		//遍历stringBuilder，从0开始到stringBuilder.length() - 1结束，i+=8表示步长是8
		//因为需要将stringBuilder按照8位截取一次，每次截取之后的字符串转换为byte类型添加到huffmanCodesBytes中
		//在压缩的时候需要多减去一个1，也就是i < stringBuilder.length() - 1，忽略最后一位
		for (int i = 0; i < stringBuilder.length() - 1; i += 8) {
			String strBytes;
			//如果截取到最后，不够8位，那么直接截取即可
			if (i + 8 > stringBuilder.length()) {
				strBytes = stringBuilder.substring(i);
			} else {
				//如果每次截取够8位，就从i截取到i+8即可
				strBytes = stringBuilder.substring(i, i + 8);
			}
			//将截取之后的字符串按照二进制转换为int类型数值，最后再强转为byte类型数值，添加到huffmanCodesBytes中
			huffmanCodesBytes[index] = (byte) Integer.parseInt(strBytes, 2);
			//每添加一次，赫夫曼树对应的数组的索引就累加1
			index++;
		}
		//最后返回压缩之后的赫夫曼树对应的数组
		return huffmanCodesBytes;
	}

	/**
	 * 重载getHuffmanCodes(HuffmanNode root, String code, StringBuilder builder)方法
	 *
	 * @param root 赫夫曼树的根节点
	 * @return 赫夫曼树的编码
	 */
	public static Map<Byte, String> getHuffmanCodes(HuffmanNode root) {
		if (root == null) {
			return null;
		}
		//向左递归
		getHuffmanCodes(root.getLeft(), "0", stringBuilder);

		//向右递归
		getHuffmanCodes(root.getRight(), "1", stringBuilder);

		return huffmanCodes;
	}

	/**
	 * 将赫夫曼树转换为赫夫曼编码
	 *
	 * @param root    根节点
	 * @param code    路径，左子节点为0，右子节点为1
	 * @param builder 拼接从根节点到每一个叶子节点的路径
	 */
	public static void getHuffmanCodes(HuffmanNode root, String code, StringBuilder builder) {
		StringBuilder stringBuilder = new StringBuilder(builder);
		stringBuilder.append(code);
		//从根节点开始，需要判断根节点是否为空
		if (root != null) {
			//如果不为空，继续判断是否是非叶子节点
			if (root.getData() == null) {
				//如果是非叶子节点
				//向左递归
				getHuffmanCodes(root.getLeft(), "0", stringBuilder);
				//向右递归
				getHuffmanCodes(root.getRight(), "1", stringBuilder);
			} else {
				//如果是叶子节点，那么记录叶子节点的
				//Map的key保存数据（字符），Value保存每一个字符规定的编码
				//例如： = 01, 97 = 100, 100 = 100111
				huffmanCodes.put(root.getData(), stringBuilder.toString());
			}
		}
	}

	/**
	 * 创建赫夫曼树
	 *
	 * @param huffmanNodes List集合
	 * @return 返回赫夫曼树的根节点root
	 */
	public static HuffmanNode createHuffmanTree(List<HuffmanNode> huffmanNodes) {
		HuffmanNode leftHuffmanNode = null;
		HuffmanNode rightHuffmanNode = null;
		HuffmanNode parent = null;
		while (huffmanNodes.size() > 1) {
			//从小到大排序
			Collections.sort(huffmanNodes);

			//取出第一个最小的二叉树和第二个最小的二叉树
			leftHuffmanNode = huffmanNodes.get(0);
			rightHuffmanNode = huffmanNodes.get(1);
			//创建一个新的二叉树，根节点没有data数据，只有weight权值，所以为null，
			parent = new HuffmanNode(null, leftHuffmanNode.getWeight() + rightHuffmanNode.getWeight());

			parent.setLeft(leftHuffmanNode);
			parent.setRight(rightHuffmanNode);

			//移除List集合中处理过之后的第一个二叉树和第二个二叉树
			huffmanNodes.remove(leftHuffmanNode);
			huffmanNodes.remove(rightHuffmanNode);

			//添加新的二叉树到nodes
			huffmanNodes.add(parent);
		}
		//返回nodes的最后一个节点，也就是赫夫曼树的根节点
		return huffmanNodes.get(0);
	}

	/**
	 * 接收一个字节数组，返回Node节点组成的List集合
	 *
	 * @param bytes 字节数组
	 * @return 返回Node节点的结合，Node的data存放数据，Node的weight存放权值
	 */
	public static List<HuffmanNode> getNodes(byte[] bytes) {
		List<HuffmanNode> huffmanNodes = new ArrayList<>();

		//Byte记录数据，也就是字符，Integer记录权值，也就是字符出现的次数
		Map<Byte, Integer> counts = new HashMap<>();

		for (byte b : bytes) {
			//首先获取字符出现的次数
			Integer count = counts.get(b);
			//如果没有记录到该字符出现的次数
			if (count == null) {
				//次数记录为1
				counts.put(b, 1);
			} else {
				//如果记录的优有次数，那么次数累加1
				counts.put(b, count + 1);
			}
		}

		//遍历Map，将Node添加到List集合中，将Byte作为Node的数据data，将Integer作为Node的权值weight
		for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
			Byte data = entry.getKey();
			Integer weight = entry.getValue();
			huffmanNodes.add(new HuffmanNode(data, weight));
		}

		return huffmanNodes;
	}
}
