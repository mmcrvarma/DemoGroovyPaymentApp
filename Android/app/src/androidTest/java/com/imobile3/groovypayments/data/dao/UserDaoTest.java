package com.imobile3.groovypayments.data.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.imobile3.groovypayments.data.GroovyDatabase;
import com.imobile3.groovypayments.data.TestDataRepository;
import com.imobile3.groovypayments.data.entities.UserEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest
{
    private GroovyDatabase mGroovyDatabase;

    @Before
    public void setUp()
    {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.deleteDatabase(GroovyDatabase.NAME);
        mGroovyDatabase = Room.inMemoryDatabaseBuilder(context, GroovyDatabase.class)
                .build();
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void insertUsers()
    {
        List<UserEntity> userEntities = TestDataRepository.getInstance()
                .getUsers(TestDataRepository.Environment.InstrumentationTest);

        // Insert entities into database instance.
        mGroovyDatabase.getUserDao().insertUsers(userEntities.toArray(new UserEntity[0]));
        List<UserEntity> userResults =
                mGroovyDatabase.getUserDao().getUsers();

        //Verify user exists
        assertNotNull(userResults);
        assertFalse(userResults.get(0).getUsername().isEmpty());
    }

    @Test
    public void deleteUsers()
    {
        insertUsers();

        //Get list of users.
        List<UserEntity> userResults =
                mGroovyDatabase.getUserDao().getUsers();
        mGroovyDatabase.getUserDao().deleteUsers(userResults.toArray(new UserEntity[0]));

        //Get results after delete
        userResults =
                mGroovyDatabase.getUserDao().getUsers();

        //Verify no user exists
        assertTrue(userResults.isEmpty());
    }

    @Test
    public void updateUsers()
    {
        insertUsers();

        //Get list of users.
        List<UserEntity> userResults =
                mGroovyDatabase.getUserDao().getUsers();
        for (UserEntity entity : userResults)
        {
            entity.setLastName("New Last Name");
        }
        mGroovyDatabase.getUserDao().updateUsers(userResults.toArray(new UserEntity[0]));

        //Get results after update
        userResults =
                mGroovyDatabase.getUserDao().getUsers();

        //Verify no user exists
        assertEquals(userResults.get(0).getLastName(), "New Last Name");
    }
}
