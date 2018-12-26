import java.util.Arrays;

public class ScaleConstructor {
	
	private int key;
	private String keyString;
	private Mode mode;
	private boolean flats = true;
	private int[] scale;
	//===========================================================================================
	static enum Scale{ MAJOR, DORIAN, PHRYGIAN, LYDIAN, MIXOLYDIAN, MINOR, LOCRIAN }
	//===========================================================================================
	//-------------------------------------------------------------------------------------------
	public ScaleConstructor(int key, Scale scale, boolean flats){
		this.flats = flats;
		this.key = key;
		this.mode = new Mode(scale);
		this.keyString = convertKeyToString(key);
		this.scale = getScale();
	}
	//-------------------------------------------------------------------------------------------
	public String convertKeyToString(int key){
		switch(key%12){
			case 0: return "C";
			case 1:
				if(flats) return "Db";
				else return "C#";
			case 2: return "D";
			case 3: 
				if(flats) return "Eb";
				else return "D#";
			case 4: return "E";
			case 5: return "F";
			case 6: 
				if(flats) return "Gb";
				else return "F#";
			case 7: return "G";
			case 8: 
				if(flats) return "Ab";
				else return "G#";
			case 9: return "A";
			case 10: 
				if(flats) return "Bb";
				else return "A#";
			case 11: return "B";
			default: return "DNE";
		}
	}
	//-------------------------------------------------------------------------------------------
	public int[] getScale(){
		int[] scale = new int[mode.getIntervals().length];
		int keyIndex = key;
		for(int index = 0; index < mode.getIntervals().length; index++){
			scale[index] = keyIndex;
			keyIndex += mode.getIntervals()[index];
		}
		return scale;
	}
	//-------------------------------------------------------------------------------------------
	private static int[] convertToInterval(int[] notes){
		int [] result = new int[notes.length];
		for(int index = 0; index < notes.length; index++){
			result[index] = (notes[index] % 12) - (notes[0] % 12);
			if(result[index] < 0){
				result[index] = 12 + result[index];
			}
		}
		
		return result;
	}
	//-------------------------------------------------------------------------------------------
	private static String getQuality(int [] intervals){
		if(Arrays.equals(intervals, new int[]{0,4,7})) return "Major";
		else if(Arrays.equals(intervals, new int[]{0,3,7})) return "Minor";
		else if(Arrays.equals(intervals, new int[]{0,4,8})) return "Augmented";
		else if(Arrays.equals(intervals, new int[]{0,3,6})) return "Diminished";
		else return "Error: Quality Not Found";
	}
	//-------------------------------------------------------------------------------------------
	public void printResults(){
		System.out.println("Key: " + keyString);
		System.out.println("");
		System.out.println("****************Scale*******************");
		printKeys(scale);
		System.out.println();
		System.out.println("****************Triads******************");
		for(int index = 0; index < scale.length; index++){
			int[] triad = getTriad(index);
			System.out.print(convertKeyToString(triad[0]) + " " + getQuality(convertToInterval(triad)) + ": ");
			printKeys(triad);
			System.out.println();
		}
		System.out.println("****************************************");
	}
	//-------------------------------------------------------------------------------------------
	public int[] getTriad(int key){
		int [] result = new int[3];
		
		result[0] = scale[key % scale.length];
		result[1] = scale[(key + 2) % scale.length];
		result[2] = scale[(key + 4) % scale.length];
		
		return result;
	}
	//-------------------------------------------------------------------------------------------
	public void printKeys(int[] keys){
		for(int index = 0; index < keys.length; index++){
			System.out.print(convertKeyToString(keys[index]) + (index == (keys.length - 1) ? "" : ", "));
		}
	}
	//-------------------------------------------------------------------------------------------
	public static void main(String[] args){
		ScaleConstructor test = new ScaleConstructor(6, Scale.MIXOLYDIAN, true);
		test.printResults();
	}
	//===========================================================================================
	class Mode{
		private int [] intervals;
		public Mode(Scale scale){
			switch(scale){
				case MAJOR: intervals = new int[] {2,2,1,2,2,2,1}; break;
				case DORIAN: intervals = new int[] {2,1,2,2,2,1,2}; break;
				case PHRYGIAN: intervals = new int[] {1,2,2,2,1,2,2}; break;
				case LYDIAN: intervals = new int[] {2,2,2,1,2,2,1}; break;
				case MIXOLYDIAN: intervals = new int[] {2,2,1,2,2,1,2}; break;
				case MINOR: intervals = new int[] {2,1,2,2,1,2,2}; break;
				case LOCRIAN: intervals = new int[] {1,2,2,1,2,2,2}; break;
			}
		}
		public int[] getIntervals(){
			return this.intervals;
		}
	}
	//===========================================================================================
}