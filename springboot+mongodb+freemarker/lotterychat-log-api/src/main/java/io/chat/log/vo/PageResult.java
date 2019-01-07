package io.chat.log.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 分页数据包装类
* @Description: TODO(用一句话描述该文件做什么)
* @author sheji zhaosheji.kevin@gmail.com
* @date 2019年1月4日
 */
@Data
public class PageResult<T> implements Serializable {

	private static final long serialVersionUID = 6400059919411211054L;
	
	private int pageSize = 20; 
    private int currentPage = 1;
    private Long totalCount = 0L; 
    private Long totalPage = 0L; 
    private List<T> list;

    public PageResult(List<T> list,Long totalCount) {
        this.list = list;
        this.totalCount = totalCount;
        setTotalCount(totalCount);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize > 0 && pageSize <=1000 ){
            this.pageSize = pageSize;
        }else{
            this.pageSize = 20;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage > 0 && currentPage<= this.totalPage){
            this.currentPage = currentPage;
        }else{
            this.currentPage = 1;
        }
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        if( this.totalCount % pageSize == 0){
            this.totalPage =  this.totalCount / pageSize;
        }else{
            this.totalPage =  this.totalCount / pageSize +1;
        }
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
