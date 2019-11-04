package javax.main.functional.demo;

import fj.data.List;
import fj.data.State;

import static javax.main.functional.demo.StateDemo_VendingMachine.Input.COIN;
import static javax.main.functional.demo.StateDemo_VendingMachine.Input.TURN;

/**
 * Created by MarkPerry on 20/07/2014.
 */
public class StateDemo_VendingMachine {

	public enum Input { COIN, TURN };

	public static class VendingMachine {

		private boolean locked;
		private int items;
		private int coins;

		public VendingMachine(boolean lock, int things, int numCoins) {
			locked = lock;
			items = things;
			coins = numCoins;
		}

		/**
		 * Equals generated by Intellij
		 */
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			VendingMachine that = (VendingMachine) o;

			if (coins != that.coins) return false;
			if (items != that.items) return false;
			if (locked != that.locked) return false;

			return true;
		}

		/**
		 * HashCode generated by Intellij
		 */
		@Override
		public int hashCode() {
			int result = (locked ? 1 : 0);
			result = 31 * result + items;
			result = 31 * result + coins;
			return result;
		}

		public String toString() {
			return String.format("VendingMachine(locked=%b,items=%d,coins=%d)", locked, items, coins);
		}

		VendingMachine next(Input i) {
			if (items == 0) {
				return this;
			} else if (i == COIN && !locked) {
				return this;
			} else if (i == TURN && locked) {
				return this;
			} else if (i == COIN && locked) {
				return new VendingMachine(false, items, coins + 1);
			} else if (i == TURN && !locked) {
				return new VendingMachine(true, items - 1, coins);
			} else {
				return this;
			}
		}
	}

	static State<VendingMachine, VendingMachine> simulate(List<Input> list) {
		return list.foldLeft((s, i) -> s.map(m -> m.next(i)), State.<VendingMachine>init());
	}

	static void test() {
		State<VendingMachine, VendingMachine> s = simulate(List.list(COIN, TURN, TURN, COIN, COIN, TURN));
		VendingMachine m = s.eval(new VendingMachine(true, 5, 0));
        System.out.println(s.run(new VendingMachine(true, 5, 0)));
		VendingMachine oracle = new VendingMachine(true, 3, 2);
		System.out.printf("m1: %s, oracle: %s, equals: %b", m, oracle, m.equals(oracle));
	}

	public static void main(String args[]) {
		test();
	}

}