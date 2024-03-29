package com.example.bssapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.bssapp.db.models.ClassItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "class_items".
*/
public class ClassItemDao extends AbstractDao<ClassItem, Long> {

    public static final String TABLENAME = "class_items";

    /**
     * Properties of entity ClassItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ClassId = new Property(0, Long.class, "classId", true, "_id");
        public final static Property SportId = new Property(1, Long.class, "sportId", false, "SPORT_ID");
        public final static Property ClassDateTime = new Property(2, java.util.Date.class, "classDateTime", false, "CLASS_DATE_TIME");
        public final static Property ClassDayOfWeek = new Property(3, int.class, "classDayOfWeek", false, "CLASS_DAY_OF_WEEK");
        public final static Property SpotId = new Property(4, Long.class, "spotId", false, "SPOT_ID");
        public final static Property Observations = new Property(5, String.class, "observations", false, "OBSERVATIONS");
        public final static Property CreateDate = new Property(6, java.util.Date.class, "createDate", false, "CREATE_DATE");
        public final static Property Deleted = new Property(7, boolean.class, "deleted", false, "DELETED");
    }


    public ClassItemDao(DaoConfig config) {
        super(config);
    }
    
    public ClassItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"class_items\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: classId
                "\"SPORT_ID\" INTEGER," + // 1: sportId
                "\"CLASS_DATE_TIME\" INTEGER," + // 2: classDateTime
                "\"CLASS_DAY_OF_WEEK\" INTEGER NOT NULL ," + // 3: classDayOfWeek
                "\"SPOT_ID\" INTEGER," + // 4: spotId
                "\"OBSERVATIONS\" TEXT," + // 5: observations
                "\"CREATE_DATE\" INTEGER," + // 6: createDate
                "\"DELETED\" INTEGER NOT NULL );"); // 7: deleted
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"class_items\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ClassItem entity) {
        stmt.clearBindings();
 
        Long classId = entity.getClassId();
        if (classId != null) {
            stmt.bindLong(1, classId);
        }
 
        Long sportId = entity.getSportId();
        if (sportId != null) {
            stmt.bindLong(2, sportId);
        }
 
        java.util.Date classDateTime = entity.getClassDateTime();
        if (classDateTime != null) {
            stmt.bindLong(3, classDateTime.getTime());
        }
        stmt.bindLong(4, entity.getClassDayOfWeek());
 
        Long spotId = entity.getSpotId();
        if (spotId != null) {
            stmt.bindLong(5, spotId);
        }
 
        String observations = entity.getObservations();
        if (observations != null) {
            stmt.bindString(6, observations);
        }
 
        java.util.Date createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(7, createDate.getTime());
        }
        stmt.bindLong(8, entity.getDeleted() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ClassItem entity) {
        stmt.clearBindings();
 
        Long classId = entity.getClassId();
        if (classId != null) {
            stmt.bindLong(1, classId);
        }
 
        Long sportId = entity.getSportId();
        if (sportId != null) {
            stmt.bindLong(2, sportId);
        }
 
        java.util.Date classDateTime = entity.getClassDateTime();
        if (classDateTime != null) {
            stmt.bindLong(3, classDateTime.getTime());
        }
        stmt.bindLong(4, entity.getClassDayOfWeek());
 
        Long spotId = entity.getSpotId();
        if (spotId != null) {
            stmt.bindLong(5, spotId);
        }
 
        String observations = entity.getObservations();
        if (observations != null) {
            stmt.bindString(6, observations);
        }
 
        java.util.Date createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(7, createDate.getTime());
        }
        stmt.bindLong(8, entity.getDeleted() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ClassItem readEntity(Cursor cursor, int offset) {
        ClassItem entity = new ClassItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // classId
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // sportId
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // classDateTime
            cursor.getInt(offset + 3), // classDayOfWeek
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // spotId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // observations
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)), // createDate
            cursor.getShort(offset + 7) != 0 // deleted
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ClassItem entity, int offset) {
        entity.setClassId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSportId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setClassDateTime(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setClassDayOfWeek(cursor.getInt(offset + 3));
        entity.setSpotId(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setObservations(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCreateDate(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
        entity.setDeleted(cursor.getShort(offset + 7) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ClassItem entity, long rowId) {
        entity.setClassId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ClassItem entity) {
        if(entity != null) {
            return entity.getClassId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ClassItem entity) {
        return entity.getClassId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
