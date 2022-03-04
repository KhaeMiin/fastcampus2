package com.fastcampus.ch3;

import com.mysql.cj.xdevapi.PreparableStatement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {

    @Autowired
    DataSource ds;

    @Test
    public void insertUserTest() throws Exception {
        User user = new User("asdf4", "1234", "asd", "aaa@aaa.com", new Date(), "facebook", new Date());
        deleteAll();
        int rowCont = insertUser(user);

        assertTrue(rowCont == 1);
    }

    @Test
    public void selectUserTest() throws Exception {
        deleteAll();
        User user = new User("asdf4", "1234", "asd", "aaa@aaa.com", new Date(), "facebook", new Date());
        int rowCnt = insertUser(user);

        User user2 = selectUser("asdf4");//asdf2를 selectUser메서드에 보내서 User로 받는다.

        assertTrue(user.getId().equals("asdf4"));
    }

    @Test
    public void deleteUserTet() throws Exception {
        deleteAll();
        int rowCnt = deleteUser("asdf");

        assertTrue(rowCnt == 0);

        deleteAll();
        User user = new User("asdf4", "1234", "asd", "aaa@aaa.com", new Date(), "facebook", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt == 1);
        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt == 1);

        assertTrue(selectUser(user.getId()) == null);

    }

    @Test
    public void updateUserTest() throws Exception {
        deleteAll();
        User user = new User("asdf4", "1234", "asd", "aaa@aaa.com", new Date(), "facebook", new Date());
        int rowCnt = updateUser(user);//만약 해당 아이디가 없는데 실행할 경우
        assertTrue(rowCnt == 0);

        deleteAll();
        rowCnt = insertUser(user);//데이터 넣기
        assertTrue(rowCnt == 1);
        User user3 = new User("asdf4", "12345", "asd6", "aaa6@aaa.com", new Date(), "facebook2", new Date());
        rowCnt = updateUser(user3);//해당 아이디가 있고 그 해당아이디에 update를 성공한 경우
        assertTrue(rowCnt == 1);

    }

    //매개변수로 받은 사용자 정보로 user_info 테이블을 update하는 메서드
    public int updateUser (User user) throws Exception {

        String sql = "update user_info set pwd = ?, name = ?, email = ?, birth=?, sns = ?, reg_date = now() where id = ?";//2. sql문 작성하고

        Connection conn = ds.getConnection();//1. 데이터소스로 부터 데이터베이스 연결을 가져온다음
        PreparedStatement pstmt = conn.prepareStatement(sql);//3. 물음표에 해당하는 값들을 채운다

        pstmt.setString(1, user.getPwd());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(5, user.getSns());
        pstmt.setString(6, user.getId());


        //executeUpdate: insert, delete, update 사용시
        return pstmt.executeUpdate();//4. sql 쿼리 실행


    }

    public int deleteUser(String id) throws Exception {
        Connection conn = ds.getConnection();//1. 데이터소스로 부터 데이터베이스 연결을 가져온다음

        String sql = "delete from user_info where id = ?";//2. sql문 작성하고

        PreparedStatement pstmt = conn.prepareStatement(sql);//3. 물음표에 해당하는 값들을 채운다

        pstmt.setString(1,id);
//        int rowCnt = pstmt.executeUpdate();//4. sql 쿼리 실행
//        return rowCnt;
        return pstmt.executeUpdate();
    }

    public User selectUser(String id) throws Exception {

        Connection conn = ds.getConnection();//1. 데이터소스로 부터 데이터베이스 연결을 가져온다음


        String sql = "select * from user_info where id = ?";//2. sql문 작성하고

        //SQL Injection 공격, 성능향상
        PreparedStatement pstmt = conn.prepareStatement(sql);//3. 물음표에 해당하는 값들을 채운다
        pstmt.setString(1, id);


        //executeQuery: select 사용시
        ResultSet rs = pstmt.executeQuery();//4. sql 쿼리 실행

        if(rs.next()) {//쿼리실행시 결과가 있으면 아래 코드 실행
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getDate(7).getTime()));

            return user;
        }
        return null;
    }

    private void deleteAll() throws Exception {

        Connection conn = ds.getConnection();//1. 데이터소스로 부터 데이터베이스 연결을 가져온다음


        String sql = "delete from user_info";//2. sql문 작성하고

        //SQL Injection 공격, 성능향상
        PreparedStatement pstmt = conn.prepareStatement(sql);//3. 물음표에 해당하는 값들을 채운다

        //executeUpdate: insert, delete, update 사용시
        pstmt.executeUpdate();//4. sql 쿼리 실행


    }

    //사용자 정보를 user-info테이블에 저장하는 메서드
    public int insertUser(User user) throws Exception {

        Connection conn = ds.getConnection();//1. 데이터소스로 부터 데이터베이스 연결을 가져온다음


        String sql = "insert into user_info values (?, ?, ?, ?, ?, ?, now())";//2. sql문 작성하고

        //SQL Injection 공격, 성능향상
        PreparedStatement pstmt = conn.prepareStatement(sql);//3. 물음표에 해당하는 값들을 채운다
        pstmt.setString(1,user.getId());//첫번째 물음표에 getId로 채우는것
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());

        //executeUpdate: insert, delete, update 사용시
        int rowCont = pstmt.executeUpdate();//4. sql 쿼리 실행

        return rowCont;//5.rowCont에 담아서 반환
    }

    @Test//테스트할 메서드는 반드시 붙여야함 그리고 메서드는 public void 이어야함
    public void springJdbcConnectionTest() throws Exception {
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn != null);//괄호 안의 조선식이 true면 테스트 성고, 아니면 실패
    }

}