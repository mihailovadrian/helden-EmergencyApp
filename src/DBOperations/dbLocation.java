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

public class dbLocation {

	static public Location readLocation(ResultSet rs, Location loc) {
		Location result = null;
		try {
			if (rs != null) {
				if (loc == null) {
					loc = new Location();
				}
				loc.setId(rs.getInt("location_id"));
				loc.setName(rs.getString("name"));

				result = loc;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	static public Location getLocationById(AppContext context, int id) {
		Location result = null;

		Connection con = context.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from locations_mlv where location_id= ?";
		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				result = readLocation(rs, null);
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally {
			onFinally(null, rs, ps);
		}

		return result;
	}

	static public Location getLocationByName(AppContext context, String name) {
		Location result = null;

		Connection con = context.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from location_mlv where name LIKE ?";
		try {
			ps = con.prepareStatement(sql);
			System.out.println(name);
			ps.setString(1, "%" + name + "%");

			rs = ps.executeQuery();

			if (rs.next()) {
				result = readLocation(rs, null);
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally {
			onFinally(null, rs, ps);
		}

		return result;
	}

	static public List<Location> listLocation(AppContext context, List<Location> list) {
		List<Location> result = null;
		Connection con = context.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Location ev;
		String sql = "select * from location_mlv";

		try {
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			if (list == null) {
				list = new ArrayList<>();
			} else {
				list.clear();
			}

			while (rs.next()) {
				ev = readLocation(rs, null);
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
