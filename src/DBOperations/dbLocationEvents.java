package DBOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.AppContext;
import entities.*;

public class dbLocationEvents {
	static public EventLocation readEvent(ResultSet rs, EventLocation ev) {
		EventLocation result = null;
		try {
			if (rs != null) {
				if (ev == null) {
					ev = new EventLocation();
				}
				ev.setIdEvent(rs.getInt("event_id"));
				ev.setIdLocation(rs.getInt("location_id"));

				result = ev;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	static public int addEventLocation(AppContext context, EventLocation ev) {
		int result = 0;
		if (ev != null) {
			Connection con = context.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = " insert into EventLocation_mlv(event_id,location_id)  values (?, ?)";

			try {
				ps = con.prepareStatement(sql);

				ps.setLong(1, ev.getIdEvent());
				ps.setLong(2, ev.getIdLocation());

				ps.executeUpdate();

				con.commit();
			}

			catch (Exception e) {
				e.printStackTrace();
				result = 0;

				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				onFinally(null, rs, ps);
			}
		}
		return result;
	}

	static public List<EventLocation> listEventLocation(AppContext context, List<EventLocation> list) {
		List<EventLocation> result = null;
		Connection con = context.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		EventLocation ev;
		String sql = "select * from eventLocation_mlv";

		try {
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			if (list == null) {
				list = new ArrayList<>();
			} else {
				list.clear();
			}

			while (rs.next()) {
				ev = readEvent(rs, null);
				if (ev != null) {
					list.add(ev);
				}
			}

			result = list;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		} finally {
			onFinally(null, rs, ps);
		}
		return result;
	}

	public static void onFinally(Statement st, ResultSet rs, PreparedStatement ps) {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception ex) {
			}
		if (st != null)
			try {
				st.close();
			} catch (Exception ex) {
			}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
