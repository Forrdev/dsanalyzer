function Act(a1, a2, a3)
    local l4 = a1:GetDist(TARGET_ENE_0)
    local l5 = Att_Dist_max
    local l6 = Att_Dist_max + 2
    Approach(a1, a2, l5, l6, 0)
    local l7 = GET_PARAM(a3[1], 100)
    Watch(a1, a2)
    return l7
end

function Update(a1, a2)
    local l3 = a2:GetParam(0)
    local l4 = a2:GetParam(1)
    if l4 <= 0 then
        l4 = 10
    end
    if dist then
        local l5 = 3
    end
    local l6 = a1:GetNumber(1)
    a1:SetNumber(1, l6 + 1)
    for l7 = 1, l6 do
        Step(l7, l3)
    end
    local l8 = Check(l4)
    return l8
end

function Make(a1)
    local l2 = a1 * 2
    return function(a3)
        return a3 + l2
    end
end

Message = "\148.\0012"
