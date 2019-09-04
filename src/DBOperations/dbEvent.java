package DBOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.AppConstants;
import app.AppContext;
import entities.*;

public class dbEvent {
	static public Event readEvent(ResultSet rs, Event ev) {
		Event result = null;
		try {
			if (rs != null) {
				if (ev == null) {
					ev = new Event();
				}
				ev.setId(rs.getLong("event_id"));
				ev.setName(rs.getString("name"));
				ev.setDescription(rs.getString("description"));
				ev.setStatus(rs.getInt("status"));
				result = ev;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	static public Event getEventById(AppContext context, int id) {
		Event result = null;

		Connection con = context.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from events_mlv where event_id= ?";
		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				result = readEvent(rs, null);
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally {
			onFinally(null, rs, ps);
		}

		return result;
	}

	
	static public Event getEventByName(AppContext context, String name) {
		Event result = null;

		Connection con = context.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from events_mlv where name= ?";
		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				result = readEvent(rs, null);
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally {
			onFinally(null, rs, ps);
		}

		return result;
	}
	
	static public int addEvent(AppContext context, Event ev) {
		int result = 0;
		if (ev != null) {
			Connection con = context.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = " insert into events_mlv(name,description,status)  values (?, ?, ?)"; // OUTPUT
																								// Inserted.ID_USER

			try {
				ps = con.prepareStatement(sql);

				ps.setString(1, ev.getName());
				ps.setString(2, ev.getDescription());
				ps.setLong(3, ev.getStatus());
				ps.executeUpdate();

				/*
				 * rs = ps.getGeneratedKeys(); if (rs.next()) { result = rs.getInt(1); }
				 */

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

	static public boolean editEvent(AppContext context, Event ev) {
		boolean result = false;

		if (ev != null) {
			Connection con = context.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "update events_mlv set status ="+AppConstants.STATUS_EVENT_OFF+" where event_id = ?";

			try {
				ps = con.prepareStatement(sql);
		
				ps.setLong(1, ev.getId());

				ps.executeUpdate();

				con.commit();

				result = true;
			}

			catch (Exception e) {
				e.printStackTrace();
				result = false;

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

	static public List<Event> listEvents(AppContext context, List<Event> list, int filter) {
		List<Event> result = null;
		Connection con = context.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Event ev;
		String sql = "select * from events_mlv";
		if (filter == AppConstants.STATUS_EVENT_ON) {
			sql = sql + " where status=?";
		} else {
			filter = -1;
		}

		try {
			ps = con.prepareStatement(sql);

			if (filter == AppConstants.STATUS_EVENT_ON) {
				ps.setInt(1, filter);
			}

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
