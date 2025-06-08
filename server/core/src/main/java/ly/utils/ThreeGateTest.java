package ly.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Author: liuYang
 * Date: 2025/4/18
 * File: ThreeGateTest
 */
public class ThreeGateTest {

  /** 默认 3个未知的门，也支持更多的未知的门 */
  private static final int GATE_LENGTH = 3;

  static Random rand = new Random();

  /** 每种选择测试的次数 默认测试1W次* */
  private static final int MAX_TEST_NUM = 10000;

  public static void main(String[] args) {
    test1(); //  第一种策略， 主持人在剩余的门中选择一个羊之后，玩家仍旧坚持之前的选择，不进行调换。 概率应该为 33%
    test2(); //  第二种策略， 主持人在剩余的门中选择一个羊之后，玩家进行调换为另外以恶搞门。 概率应该为 66%
  }

  /** 第二种策略， 主持人在剩余的门中选择一个羊之后，玩家进行调换为另外以恶搞门。 概率应该为 66% */
  private static void test2() {
    // 玩家 成功选择到车的次数
    int successNum = 0;
    // 进行 1W次 随机
    for (int i = 0; i < MAX_TEST_NUM; i++) {

      List<GateBox> threeGateBoxes = new ArrayList<>(GATE_LENGTH);
      // 该门后面有车
      int hasCardIndex = randomThreeGateBoxes(threeGateBoxes); // 生成一组三门
      int firstChooseIndex = randomInt(threeGateBoxes.size()); // 玩家第一次选择的门
      threeGateBoxes.remove(firstChooseIndex); // 从带选择的门中移除玩家已经选择的门
      int otherHasSheepIndex = getOtherHasSheepGateIndex(threeGateBoxes); // 主持人，找到剩余俩门中 另外一个有羊的门的下标
      threeGateBoxes.removeIf(
          gateBox -> gateBox.index == otherHasSheepIndex); // 剩余的俩门中，排除掉主持人选择的有羊的门
      int switchIndex = randomInt(threeGateBoxes.size()); // 玩家从剩余的门中再次进行一次选择
      successNum += threeGateBoxes.get(switchIndex).isCard ? 1 : 0; // 如果选择的门后面有车 则记录一次
    }
    double probability = (double) successNum / MAX_TEST_NUM * 100;
    System.out.println(
        String.format(
            "玩家进行策略二(每次都调换门) 总共进行 %d 次选择， 其中选择到车的次数为 %d, 概率为为%.2f%%",
            MAX_TEST_NUM, successNum, probability));
  }

  /**
   * 主持人从剩余的俩门中选择有羊的门
   *
   * @param threeGateBoxes 剩余俩门集合
   * @return 有羊的门的下标
   */
  private static int getOtherHasSheepGateIndex(List<GateBox> threeGateBoxes) {
    return threeGateBoxes.stream().filter(gateBox -> !gateBox.isCard).findAny().get().index;
  }

  /** 第一种策略， 主持人在剩余的门中选择一个羊之后，玩家仍旧坚持之前的选择，不进行调换。 概率应该为 33% */
  private static void test1() {
    // 玩家 成功选择到车的次数
    int successNum = 0;
    // 进行 1W次 随机
    for (int i = 0; i < MAX_TEST_NUM; i++) {

      List<GateBox> threeGateBoxes = new ArrayList<>(GATE_LENGTH);
      // 该门后面有车
      int hasCardIndex = randomThreeGateBoxes(threeGateBoxes); // 生成一组三门
      int firstChooseIndex = randomInt(threeGateBoxes.size()); // 玩家第一次选择的门
      successNum += hasCardIndex == firstChooseIndex ? 1 : 0;
    }
    double probability = (double) successNum / MAX_TEST_NUM * 100;
    System.out.println(
        String.format(
            "玩家进行策略一(始终不调换门) 总共进行 %d 次选择， 其中选择到车的次数为 %d, 概率为%.2f%%",
            MAX_TEST_NUM, successNum, probability));
  }

  /**
   * 随机产生一组三门，其中一个门有车 其他的门里面全是羊
   *
   * @param threeGateBoxes 一组封装好的三门
   * @return 有车的门的下标
   */
  static int randomThreeGateBoxes(List<GateBox> threeGateBoxes) {
    // 有车的门的下标
    int hasCardGateIndex = randomInt(GATE_LENGTH);
    for (int i = 0; i < GATE_LENGTH; i++) {
      if (i == hasCardGateIndex) {
        threeGateBoxes.add(new GateBox(true, i));
      } else {
        threeGateBoxes.add(new GateBox(false, i));
      }
    }
    return hasCardGateIndex;
  }

  static int randomInt(int max) {
    // 随机数字必须大于 0
    if (max <= 0) throw new RuntimeException("max must be greater than 0");
    return rand.nextInt(max);
  }

  /** 未知的门后物品 */
  static class GateBox {
    /** true 该门后面是车, false 该门后面是羊 */
    boolean isCard;

    /** 门的序号 */
    int index;

    public GateBox(boolean isCard, int index) {
      this.isCard = isCard;
      this.index = index;
    }
  }
}
