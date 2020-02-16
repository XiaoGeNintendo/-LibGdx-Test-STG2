package com.hhs.xgn.stg.struct;

/**
 * The Enum class of render mode
 * @author XGN
 *
 */
public class RenderMode {
	/**
	 * 0=stop
	 */
	public static final int STOP=0;
	/**
	 * 0=stop <br/>
	 * 1=move R <br/>
	 * 2=cast <br/>
	 * <b>Unimplementated</b>
	 */
	public static final int STOP_MOVE_CAST=1;
	
	/**
	 * 0=stop <br/>
	 * 1=move R <br/>
	 * 2=move L <br/>
	 * 3=cast <br/>
	 * <b>Unimplementated</b>
	 */
	public static final int STOP_MOVER_L_CAST=2;
	
	/**
	 * 0=stop <br/>
	 * 1= move R <b>Only 1 frame will be played.</b> <br/>
	 * 2= cast <b>This animation is ping pong</b> <br/>
	 */
	public static final int STOP_MOVES_CAST=3;
}
