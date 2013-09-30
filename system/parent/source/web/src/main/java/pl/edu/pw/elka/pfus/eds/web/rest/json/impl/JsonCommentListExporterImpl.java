package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractSuccessFailureJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonCommentListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.CommentJsonDto;

import java.util.LinkedList;
import java.util.List;

public class JsonCommentListExporterImpl extends AbstractSuccessFailureJsonExporter implements JsonCommentListExporter {
    @Override
    public String exportSuccess(List<Comment> list) {
        List<CommentJsonDto> dtos = getCommentJsonDtos(list);
        return success(dtos);
    }

    @Override
    public String exportFailure(String errorMessage, List<Comment> list) {
        List<CommentJsonDto> dtos = getCommentJsonDtos(list);
        return failure(errorMessage, dtos);
    }

    private List<CommentJsonDto> getCommentJsonDtos(List<Comment> list) {
        List<CommentJsonDto> dtos = new LinkedList<>();
        for(Comment comment : list) {
            CommentJsonDto dto = CommentJsonDto.from(comment);
            dtos.add(dto);
        }
        return dtos;
    }
}
