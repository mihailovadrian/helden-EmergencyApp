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

public class dbClient {
	static public Client readClient(ResultSet rs, Client clt) {
		Client result = null;
		try {
			if (rs != null) {
				if (clt == null) {
					clt = new Client();
				}
				clt.setId(rs.getInt("id_client"));
				clt.setName(rs.getString("name"));
				clt.setLongitude(rs.getString("longitude"));
				clt.setLatitude(rs.getString("latitude"));
				clt.setStatus(rs.getInt("status"));
				clt.setLocation_id(rs.getInt("location_id"));
				result = clt;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	static public Client getClientById(AppContext context, int id) {
		Client result = null;

		Connection con = context.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from client_mlv where id_client= ?";
		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				result = readClient(rs, null);
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally {
			onFinally(null, rs, ps);
		}

		return result;
	}

	static public Client getClientByName(AppContext context, String name) {
		Client result = null;
		if (name != null) {
			Connection con = context.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select * from client_mlv where lower(name)=?";
			try {
				ps = con.prepareStatement(sql);

				ps.setString(1, name.toLowerCase().trim());
				rs = ps.executeQuery();
				if (rs.next()) {
					result = readClient(rs, null);
				}
			} catch (Exception e) {
				result = null;
				e.printStackTrace();
			} finally {
				onFinally(null, rs, ps);
			}

		}
		return result;
	}

	static public int addClient(AppContext context, Client clt) {
		int result = 0;
		if (clt != null) {
			Connection con = context.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = " insert into client_mlv(name,longitude,latitude,status,location_id)  values (?, ?, ?, ?,?)"; // OUTPUT
																														// Inserted.ID_USER

			try {
				ps = con.prepareStatement(sql);

				ps.setString(1, clt.getName());
				ps.setString(2, clt.getLongitude());
				ps.setString(3, clt.getLatitude());
				ps.setLong(4, clt.getStatus());
				ps.setLong(5, clt.getLocation_id());
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

	static public boolean editClient(AppContext context, Client clt) {
		boolean result = false;

		if (clt != null) {
			Connection con = context.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "update client_mlv set  longitude = ?, latitude = ?,  location_id = ? where id_client = ?";

			try {
				ps = con.prepareStatement(sql);

				ps.setString(1, clt.getLongitude());
				ps.setString(2, clt.getLatitude());
				ps.setLong(3, clt.getLocation_id());
				ps.setLong(4, clt.getId());

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

	static public boolean editClientWithStatus(AppContext context, Client clt) {
		boolean result = false;

		if (clt != null) {
			Connection con = context.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "update client_mlv set  longitude = ?, latitude = ?,status=? , location_id = ? where id_client = ?";

			try {
				ps = con.prepareStatement(sql);

				ps.setString(1, clt.getLongitude());
				ps.setString(2, clt.getLatitude());
				ps.setLong(3, clt.getStatus());
				ps.setLong(4, clt.getLocation_id());
				ps.setLong(5, clt.getId());

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

	static public List<Client> listClients(AppContext context, List<Client> list, int filter ,int filter2) {
		List<Client> result = null;
		Connection con = context.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Client clt;
		String sql = "select * from client_mlv";
		if (filter == AppConstants.STATUS_TYPE_DANGER) {
			sql = sql + " where status=?";
		} else {
			filter = -1;
		}
		
		if (filter2 == AppConstants.STATUS_TYPE_IDLE) {
			sql = sql + " or status=?";
		} else {
			filter2 = -1;
		}

		try {
			ps = con.prepareStatement(sql);

			if (filter == AppConstants.STATUS_TYPE_DANGER) {
				ps.setInt(1, filter);
			}
			if (filter2 == AppConstants.STATUS_TYPE_IDLE) {
				ps.setInt(2, filter2);
			}

			rs = ps.executeQuery();

			if (list == null) {
				list = new ArrayList<>();
			} else {
				list.clear();
			}

			while (rs.next()) {
				clt = readClient(rs, null);
				if (clt != null) {
					list.add(clt);
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
