package ua.com.alevel.data.services;

import ua.com.alevel.data.model.dto.GroupRecord;
import ua.com.alevel.data.model.dto.save.GroupRecordSave;
import ua.com.alevel.data.model.entity.StudentGroup;

public interface GroupService extends BaseService<GroupRecord, GroupRecordSave> {

    StudentGroup mapRecordToEntity(GroupRecord group);

}
